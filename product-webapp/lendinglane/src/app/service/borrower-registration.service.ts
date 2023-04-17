import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BorrowerRegistrationService {
   // private URL = "http://localhost:9002/api/v1/borrower/register";
     apiBaseUrl = environment.apiBaseUrl + "/borrower-registration-service";

  constructor(private httpClient:HttpClient) { }

  createBorrower(data:any){
    return this.httpClient.post(this.apiBaseUrl + "/api/v1/borrower/register",data);
  }

}

