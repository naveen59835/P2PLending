import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  url="http://localhost:8081/api/v1/payment/createOrder"
  urldetail="http://localhost:8081/api/v1/payment/updateOrderDetails"
  
  
    constructor(private httpclient:HttpClient) { }
  
    value:any="amount"
    status:any=""
    payment(amount:any,toname:any,fromname:any)
    {
      return this.httpclient.post<any>(`${this.url}`,{"amount":amount,"to":toname,"from":fromname})
    }
  
    updatedetail(amount:any,toname:any,fromname:any,id:any,status:any)
    {
      console.log(status+"service")
      return this.httpclient.put<any>(`${this.urldetail}`,{"amount":amount,"to":toname,"from":fromname,"id":id,"status":status})
    }

}
