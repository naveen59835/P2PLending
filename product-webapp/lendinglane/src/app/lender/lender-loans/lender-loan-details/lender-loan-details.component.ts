import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {LoanService} from "../../../service/loan.service";
import {PaymentService} from "../../../service/payment.service";
import Swal from "sweetalert2";

@Component({
  selector: 'app-lender-loan-details',
  templateUrl: './lender-loan-details.component.html',
  styleUrls: ['./lender-loan-details.component.css']
})
export class LenderLoanDetailsComponent implements OnInit {
  constructor(private activatedRoute : ActivatedRoute, private loanService : LoanService, private router : Router, private paymentService : PaymentService) { }
  loanData!:any;
  ngOnInit(): void {
    this.activatedRoute.params.subscribe({
      next :( data:any)=>this.getLoan(data?.id)
    })
  }
  getLoan(loanId : string){
    this.loanService.getLoan(loanId).subscribe({
      next : (data:any)=> {
        this.loanData = data
      },
      error : (err)=> this.router.navigateByUrl("not-found")
    })
  }
  updateDetails(data:any,emiData:any,emiId:any){
    this.paymentService.payEMI(Math.ceil(emiData.amount/100),data.lenderId, data.borrowerId, data.id,emiId,emiData.id).subscribe({
      next:data=>console.log(data)
    })
  }
  totalPaid(emi : Array<any>){
    if(emi.length>0)
      return Math.round(emi.reduce((acc,current)=>current.paymentStatus?acc+current.price:acc+0,0) * 10) / 10
    else return  0
  }


}
