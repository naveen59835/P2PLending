import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Lender } from '../model/lender';

@Injectable({
  providedIn: 'root'
})
export class LenderService {


  constructor(private httpClient:HttpClient) {

  }
//apiBaseUrl = environment.apiBaseUrl + "/lender-service";

  url:string="https://lendinglane.stackroute.io/api/v1/lender/lenderMail"
  urlupdate:string="https://lendinglane.stackroute.io/api/v1/lender/updateLender"
  urlaadharImage:string="https://lendinglane.stackroute.io/api/v1/lender/lenderImage"
  urlpanImage:string="https://lendinglane.stackroute.io/api/v1/lender/lenderPanImage"


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
