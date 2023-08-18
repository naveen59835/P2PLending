import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Lender } from '../model/lender';

@Injectable({
  providedIn: 'root'
})
export class LenderService {


  constructor(private httpClient:HttpClient) {

  }


  url:string="http://localhost:9002/api/v1/lender/lenderMail"
  urlupdate:string="http://localhost:9002/api/v1/lender/updateLender"
  urlaadharImage:string="http://localhost:9002/api/v1/lender/lenderImage"
  urlpanImage:string="http://localhost:9002/api/v1/lender/lenderPanImage"


  getLenderById(id:any)
  {
    return this.httpClient.get(`${this.url}/${id}`)
  }

  updateLender(lender:Lender,id:any)
  {
    console.log(lender)
    return this.httpClient.put<any>(`${this.urlupdate}/${id}`,lender)
  }






updateAadharImage(id:any,data:any)
{
  return this.httpClient.put<any>(`${this.urlaadharImage}/${id}`,data)
}

updatePanImage(id:any,data:any)
{
  return this.httpClient.put<any>(`${this.urlpanImage}/${id}`,data)
}




}
