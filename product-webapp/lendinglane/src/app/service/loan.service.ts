import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {coerceStringArray} from "@angular/cdk/coercion";

@Injectable({
  providedIn: 'root'
})
export class LoanService {

  constructor(private http : HttpClient) { }

  getAllLoans(id:string,role:string){
    let userData ={"id":id,"role":role}
    return this.http.post("http://localhost:9002/api/v1/loan/getAll",userData);
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
