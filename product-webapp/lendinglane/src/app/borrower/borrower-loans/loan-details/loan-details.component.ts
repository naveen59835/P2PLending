import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {LoanService} from "../../../service/loan.service";
import {PaymentService} from "../../../service/payment.service";
import Swal from 'sweetalert2';
declare  let Razorpay :any

@Component({
  selector: 'app-loan-details',
  templateUrl: './loan-details.component.html',
  styleUrls: ['./loan-details.component.css']
})
export class LoanDetailsComponent implements OnInit {

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
  payEMI(emiId:any){
     if(this.loanData!=undefined){

       let amountToPay = Math.ceil(this.loanData.emi[emiId-1].price);
       this.paymentService.payment(amountToPay,this.loanData.lenderId,this.loanData.borrowerId).subscribe({
         next : (data:any) =>{
           let options = {
             key: data.key,
             amount: data.amount,
             currency: data.currency,
             name: this.loanData.borrowerId,
             description: "transfer",
             order_id: data.id,
             handler: (handlerData: any) => {
               let paystatus = "success"
               this.updateDetails(this.loanData,data,emiId);
               Swal.fire("payment succesfful", "Well done, you  entered amount", "success")
             },
             "prefill": {
               "name": " ",
               "email": " ",
               "contact": " "
             },
             notes: {
               address: "lender address"
             },
             theme: {
               color: "#3399cc"
             },
           };
           let rzrp = new Razorpay(options)
           rzrp.open();

         }}
       )
     }
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
