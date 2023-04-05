import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {RecommendedBorrower} from 'src/app/model/RecommendedBorroer';
import {LenderService} from 'src/app/service/lender.service';
import {RecommendationService} from 'src/app/service/recommendation.service';
import {LoanService} from "../../service/loan.service";
import {PaymentService} from "../../service/payment.service";

@Component({
  selector: 'app-lender-dashboard',
  templateUrl: './lender-dashboard.component.html',
  styleUrls: ['./lender-dashboard.component.css']
})
export class LenderDashboardComponent implements OnInit {

  borrower: RecommendedBorrower[] = [];
  loans: any = [{}];

  istrue = true


  lender: any = {
    address: {
      address: undefined, city: undefined, pin: undefined, state: undefined
    }
  }

  constructor(private recommendationService: RecommendationService,private paymentService : PaymentService, private lenderservice: LenderService, private route: Router,private  loanService : LoanService) {


  }

  ngOnInit(): void {


    this.lenderservice.getLenderById(localStorage.getItem('email')).subscribe(data => {

      this.lender = data;
      if (this.lender.creditScore == null) {

        this.add();
      }


      // this.service.LenderCreditScore
      console.log(this.lender.creditScore)
      this.recommendationService.getbBorrower(this.lender.creditScore).subscribe(data => {
        console.log(data)
        this.borrower = data;
      });

    });
    this.loanService.getLenderLoans().subscribe({
      next : data => this.loans = data
    })


  }


  creditScore() {


    this.route.navigate(['/dashboard/profile'])
    this.istrue = true


  }

  add() {


    this.istrue = !this.istrue


  }
  recovered(emi : Array<any>){
    if(emi)
    return Math.round(emi.reduce((acc:any,emiVal:any)=>acc+emiVal.price,0)*10)/10;
    else return 0
  }

}
