import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BorrowerRegistrationService {
  private URL = "http://localhost:9002/api/v2/borrower";

  constructor(private httpClient:HttpClient) { }

  createBorrower(data:any):Observable<Object>{
    return this.httpClient.post(this.URL,data);
  }

}

