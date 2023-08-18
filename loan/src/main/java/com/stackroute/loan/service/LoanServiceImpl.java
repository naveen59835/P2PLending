package com.stackroute.loan.service;

import com.stackroute.loan.configuartion.EmiDTO;
import com.stackroute.loan.configuartion.LoanDTO;
import com.stackroute.loan.model.EMI;
import com.stackroute.loan.model.Loan;
import com.stackroute.loan.proxy.BorrowerProxy;
import com.stackroute.loan.repository.LoanRepository;
import org.json.simple.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class LoanServiceImpl implements LoanService {

    LoanRepository loanRepository;

    RabbitTemplate template;

    BorrowerProxy borrowerProxy;

    @Autowired
    public LoanServiceImpl(LoanRepository loanRepository, RabbitTemplate template, BorrowerProxy borrowerProxy) {
        this();
        this.loanRepository = loanRepository;
        this.template = template;
        this.borrowerProxy = borrowerProxy;
    }

    public final Map<Integer,Double> loanRate=new HashMap<>();
    public LoanServiceImpl(){
        loanRate.put(3,16d);
        loanRate.put(6,17d);
        loanRate.put(12,19d);
        loanRate.put(24,20d);
    }
    @Override
    public Loan applyLoan(Loan loan){
        System.out.println(loan);
        loan.setInterestRate(loanRate.get(loan.getTerms()));
        loan.setDateOfLoan(LocalDate.now());
        if(hasDocuments(loan.getBorrowerId())) {
            LoanDTO loanData = new LoanDTO();
            loan = loanRepository.save(loan);
            System.out.println(loan);
            loanData.setSelectiveJsonObject(loan);
            template.convertAndSend("loan-notification-exchange","route-key",loan.getBorrowerId());
            template.convertAndSend("recommendation-exchange","route-key",loanData.getJsonObject());
            return loan;
        }
        else throw new RuntimeException("Please complete the borrower profile");
    }

//    This method is used with rabbit listener
    @Override
    @RabbitListener(queues = "loan-approval")
    public void approveLoan(JSONObject jsonObject){
        String loanId = jsonObject.get("loanId").toString();
        String lenderId = jsonObject.get("sender").toString();
        if(loanRepository.findById(loanId).isPresent()){
            Loan loan = loanRepository.findById(loanId).get();
            loan.setApproved(true);
            loan.setLenderId(lenderId);
            loanRepository.save(loan);
            //send data to notification service
            //send signal to remove loan from recommendation service

        }
    }
    @Override
    @RabbitListener(queues = "pay-emi")
    public void emiPaid(JSONObject object){
        EmiDTO emiDTO = new EmiDTO(object);
        Optional<Loan> loan = loanRepository.findById(emiDTO.getLoanId());
        if(loan.isPresent()){
            List<EMI> emiList = loan.get().getEmi();
            EMI emi = emiList.get(emiDTO.getEmiId()-1);
            emi.setPaymentId(emiDTO.getPaymentId());
            emi.setPaymentStatus(true);
            emi.setPaymentDate(emiDTO.getTime()); //Will be changed
            System.out.println(loan.get());
            loanRepository.save(loan.get());
        }
    }
    @Override
    public Loan getLoan(String loanId){
        if(loanRepository.findById(loanId).isPresent()){
            Loan loan =  loanRepository.findById(loanId).get();
            List<EMI> emiList = loan.getEmi();
            for (EMI emi : emiList) {
                emi.setPrice(emi.getPrice()+emi.getLateFee());
            }
            loan.setEmi(emiList);
            return loan;
        }
        throw new RuntimeException("Can't find the loan");
    }
    @Override
    public List<Loan> getLoans(String id, String role){
        if(role.equals("lender")){
            return loanRepository.findLoansByLenderId(id);
        }
        else{
            return loanRepository.findLoansByBorrowerId(id);
        }
    }
    @Override
//    @Scheduled(cron = "0 0 0 * * *",zone = "Asia/Kolkata")
    @Scheduled(cron = "0 * * * * *",zone = "Asia/Kolkata")
    public void addEMI(){
        List<Loan> loanList = loanRepository.findAll();
        LocalDate currentDate = LocalDate.now();
        for (Loan loan : loanList) {
            LocalDate loanStartDate = loan.getDateOfLoan();
            if(!loan.isExpired() && loan.isApproved()){
                int daysSinceLoan = (int)ChronoUnit.DAYS.between(loanStartDate,currentDate);
                int termsToAdd = (daysSinceLoan/28) - loan.getEmi().size();
                double basePrice = loan.getAmount()/loan.getTerms();
                for (int term = 0; term < termsToAdd; term++) {
                    EMI emi = new EMI(loan.getEmi().size()+1,basePrice+(basePrice*loan.getInterestRate()*0.001),false);
                    loan.addToEMI(emi);
                }
                if(termsToAdd>0){
                    loanRepository.save(loan);
                }
//                double basePrice = loan.getAmount()/loan.getTerms();
//                EMI emi = new EMI(loan.getEmi().size()+1,basePrice+(basePrice*loan.getInterestRate()*0.001),false);
//                loan.addToEMI(emi);
//                loanRepository.save(loan);
            }
            //Add late fee to EMIS
            List<EMI> emiList = loan.getEmi();
            for (EMI emi : emiList) {
                int termsSinceEmi = (int)ChronoUnit.DAYS.between(LocalDate.now(),emi.getStartDate())/28;
                if(currentDate.isAfter(emi.getStartDate().plusDays(28)) && !emi.isLateFeeAdded() && !emi.isPaymentStatus()){
                    emi.setLateFeeAdded(true);
                }
                if(emi.isLateFeeAdded() && termsSinceEmi>1 && !emi.isPaymentStatus()){
                    emi.setLateFee(emi.getPrice()+(loan.getAmount()*((termsSinceEmi-1)*5*0.01)));
                }
            }

        }
    }

    //This method uses rabbitmq data from payment microservice
    @Override
    public void payEMI(String loanId, int emiID, String paymentId){
        if(loanRepository.findById(loanId).isPresent()){
            Loan loan = loanRepository.findById(loanId).get();
            for (EMI emi : loan.getEmi()) {
                if(emi.getId() == emiID){
                    emi.setPaymentStatus(true);
                    emi.setPaymentDate(LocalDate.now());
                    emi.setPaymentId(paymentId);
                    break;
                }
            }
            loanRepository.save(loan);
        }
    }

    @Override
    public boolean hasDocuments(String id){
        Map<Object,Object> borrowerData = borrowerProxy.getBorrowerData(id);
        for (Object dataKey : borrowerData.keySet()) {
            if(borrowerData.get(dataKey)==null || borrowerData.get(dataKey).equals("")){
                return false;
            }
        }
        return true;
    }

    @Override
    public List<Loan> findLoansFromLender(String id) {
        return  loanRepository.findLoansByLenderId(id);
    }
}
