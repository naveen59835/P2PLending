import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  url="http://localhost:9002/api/v1/payment/createOrder"
  urldetail="http://localhost:9002/api/v1/payment/updateOrderDetails"
  baseUrl = "http://localhost:9002/api/v1/payment";
    constructor(private httpclient:HttpClient) { }

    value:any="amount"
    status:any=""
    payment(amount:any,toname:any,fromname:any)
    {
      return this.httpclient.post<any>(`${this.url}`,{"amount":amount,"to":toname,"from":fromname})
    }

    updatedetail(amount:any,toname:any,fromname:any,id:any,status:any,loanId:any)
    {
      console.log(id)
      console.log("to :",toname)
      return this.httpclient.put<any>(`${this.urldetail}`,{"amount":amount,"to":toname,"from":fromname,"id":id,"status":status,"loanId":loanId})
    }
    payEMI (amount :any, lenderId : any, borrowerId : any, loanId :any, emiId :any, paymentId :any){
      return this.httpclient.put("http://localhost:9002/api/v1/payment/payEMI",{"amount" :amount, "lenderId" : lenderId, "borrowerId" : borrowerId, "loanId" :loanId, "emiId" :emiId,"paymentId":paymentId})
    }
    getALlPayment(){
      return this.httpclient.get("http://localhost:9002/api/v1/payment/getPayment/"+localStorage.getItem("email"));
    }
    sortPayment(payments : Array<any>){
      return payments.sort((payment1:any,payment2:any)=>new Date(payment2.paymentDate).valueOf() - new Date(payment1.paymentDate).valueOf())
    }
    totalPayments(){
      return this.httpclient.get(this.baseUrl+"/getAllPayments");
    }
}
