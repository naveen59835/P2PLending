import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {coerceStringArray} from "@angular/cdk/coercion";

@Injectable({
  providedIn: 'root'
})
export class LoanService {
  valid:boolean=false;
  constructor(private http : HttpClient) { }

  getAllLoans(id:string,role:string){
    return this.http.get(`http://localhost:9002/api/v1/loan/Loan?id=${id}&role=${role}`);
  }
  applyLoan(data :any){
    data.borrowerId = localStorage.getItem("email");
    return this.http.post("http://localhost:9002/api/v1/loan/create",data);
  }
  getLoan(loanId:string){
    return this.http.get("http://localhost:9002/api/v1/loan/getLoan/"+loanId)
  }
  getLenderLoans(){
    return this.http.get("http://localhost:9002/api/v1/loan/getLenderLoan/"+localStorage.getItem("email"))

  }

}
