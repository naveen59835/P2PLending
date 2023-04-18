import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BorrowerDetailsService {
  //private baseUrl = 'http://localhost:9002/api/v1/borrower';
  //apiBaseUrl = environment.apiBaseUrl + "/borrower-details";

  baseurl = 'http://localhost:8080/api/v1'

  constructor(private http: HttpClient) { }

  getBorrowerDetails(emailId: string): Observable<any> {
    //return this.http.get(this.baseUrl + "/api/v1/borrower/{emailId}")
    //return this.http.get(`${this.apiBaseUrl}/borrower/${emailId}`);

      return this.http.get(`${this.baseUrl}/borrower/${emailId}`);
  }
}
