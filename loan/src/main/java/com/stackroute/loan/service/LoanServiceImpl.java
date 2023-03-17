package com.stackroute.loan.service;

import com.stackroute.loan.model.Loan;
import com.stackroute.loan.repository.LoanRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanServiceImpl {
    @Autowired
    LoanRepository loanRepository;

    public void applyLoan(Loan loan){
        loanRepository.save(loan);
        //send the data to the recommendation service
        //Send the data to the notification service
    }
    public void approveLoan(String loanId, String lenderId){
        if(loanRepository.findById(loanId).isPresent()){
            Loan loan = loanRepository.findById(loanId).get();
            loan.setApproved(true);
            loan.setLenderId(lenderId);
            loanRepository.save(loan);
            //send data to notification service
            //send signal to remove loan from reccomendation service
        }
    }
    public List<Loan> getLoans(String id, String role){
        if(role.equals("lender")){
            return loanRepository.findLoansByLenderId(id);
        }
        else{
            return loanRepository.findLoansByBorrowerId(id);
        }
    }
    //Add a new Emi every month
    @Scheduled(cron = "0 0 0 1 * ?")
    public void addEMI(){

    }

    public void payEMI(){

    }

}
