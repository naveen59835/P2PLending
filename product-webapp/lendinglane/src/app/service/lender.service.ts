import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Address } from '../model/lender';
import { Lender } from '../model/lender';

@Injectable({
  providedIn: 'root'
})
export class LenderService {
  lender:any=""
  
  constructor(private httpClient:HttpClient) { 
  
  }


  url:string="  http://localhost:8082/api/v1/lender/lenderMail"
  urlupdate:string="http://localhost:8082/api/v1/lender/updateLender"
  urlupdateAddress:string="http://localhost:8082/api/v1/lender/updateLenderAddress"


  getLenderById(id:any)
  {
    return this.httpClient.get(`${this.url}/${id}`)
  }

  updateLender(lender:Lender,id:any)
  {
    return this.httpClient.put<any>(`${this.urlupdate}/${id}`,lender)
  }




  updateLenderAddress(address:Address,id:any)
  {
    return this.httpClient.put<any>(`${this.urlupdateAddress}/${id}`,address)
  }



  

 
}
