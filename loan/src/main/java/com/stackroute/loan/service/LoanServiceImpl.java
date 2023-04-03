package com.stackroute.loan.service;

import com.stackroute.loan.model.EMI;
import com.stackroute.loan.model.Loan;
import com.stackroute.loan.proxy.BorrowerProxy;
import com.stackroute.loan.repository.LoanRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LoanServiceImpl {
    @Autowired
    LoanRepository loanRepository;
    @Autowired
    RabbitTemplate template;
    @Autowired
    BorrowerProxy borrowerProxy;
    public final Map<Integer,Double> loanRate=new HashMap<>();
    public LoanServiceImpl(){
        loanRate.put(3,16d);
        loanRate.put(6,17d);
        loanRate.put(12,19d);
        loanRate.put(24,20d);
    }
    public Loan applyLoan(Loan loan){
        loan.setInterestRate(loanRate.get(loan.getTerms()));
        loan.setDateOfLoan(LocalDate.now());
        if(hasDocuments(loan.getBorrowerId())) {
            template.convertAndSend("loan-notification-exchange","route-key",loan.getBorrowerId());
            return loanRepository.save(loan);
        }
        else throw new RuntimeException("Please complete the borrower profile");
    }

    //This method is used with rabbit listener
    public void approveLoan(String loanId, String lenderId){
        if(loanRepository.findById(loanId).isPresent()){
            Loan loan = loanRepository.findById(loanId).get();
            loan.setApproved(true);
            loan.setLenderId(lenderId);
            loanRepository.save(loan);
            //send data to notification service
            //send signal to remove loan from recommendation service
        }
    }
    public Loan getLoan(String loanId){
        if(loanRepository.findById(loanId).isPresent()){
            return loanRepository.findById(loanId).get();
        }
        throw new RuntimeException("Can't find the loan");
    }
    public List<Loan> getLoans(String id, String role){
        if(role.equals("lender")){
            return loanRepository.findLoansByLenderId(id);
        }
        else{
            return loanRepository.findLoansByBorrowerId(id);
        }
    }
    @Scheduled(cron = "0 0 0 * * *",zone = "Asia/Kolkata")
    public void addEMI(){
        List<Loan> loanList = loanRepository.findAll();
        LocalDate currentDate = LocalDate.now();
        for (Loan loan : loanList) {
            LocalDate loanDate = loan.getDateOfLoan();
            if(!loan.isExpired() && loanDate.isBefore(currentDate) && loanDate.getDayOfMonth() == currentDate.getDayOfMonth()){
                double basePrice = loan.getAmount()/loan.getTerms();
                EMI emi = new EMI(loan.getEmi().size()+1,basePrice+(basePrice*loan.getInterestRate()*0.001),false);
                loan.addToEMI(emi);
                loanRepository.save(loan);
            }
        }
    }
    //This method uses rabbitmq data from payment microservice
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

    public boolean hasDocuments(String id){
        Map<Object,Object> borrowerData = borrowerProxy.getBorrowerData(id);
        for (Object dataKey : borrowerData.keySet()) {
            if(borrowerData.get(dataKey)==null || borrowerData.get(dataKey).equals("")){
                return false;
            }
        }
        return true;
    }

}
