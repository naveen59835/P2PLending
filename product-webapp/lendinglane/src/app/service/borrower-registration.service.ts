import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BorrowerRegistrationService {
   private URL = "https://lendinglane.stackroute.io/api/v1/borrower/register";
    //apiBaseUrl = environment.apiBaseUrl + "/borrower-registration-service";

  constructor(private httpClient:HttpClient) { }

  createBorrower(data:any){
    return this.httpClient.post(this.URL,data);
  }

}

