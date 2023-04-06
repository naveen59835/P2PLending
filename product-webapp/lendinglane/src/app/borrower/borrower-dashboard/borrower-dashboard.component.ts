import { Component, OnInit } from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {LoanService} from "../../service/loan.service";

@Component({
  selector: 'app-borrower-dashboard',
  templateUrl: './borrower-dashboard.component.html',
  styleUrls: ['./borrower-dashboard.component.css']
})
export class BorrowerDashboardComponent implements OnInit {
  loans:Array<any>=[];
  constructor(private loanService : LoanService) { }

  ngOnInit(): void {
    let id = localStorage.getItem("email") || "";
    let role = localStorage.getItem("role") || "";
    this.loanService.getAllLoans(id,role).subscribe({
      next : (data:any)=> this.loans = data
    })
  }
  totalPaid(emi : Array<any>){
    if(emi.length>0)
    return Math.round(emi.reduce((acc,current)=>acc+current.price,0) * 10) / 10
    else return  0
  }

  get ongoingLoans(){
    return this.loans.filter(loan=>loan.approved && !loan.expired)
  }

  get finishedLoans(){
    return this.loans.filter(loan=>loan.approved && loan.expired);
  }

  get borrowerName(){
    return localStorage.getItem("userName")
  }
  progress(loan : any){
    let totalPaid = this.totalPaid(loan.emi);
    let percentage = (totalPaid/loan.amount)*100;
    console.log(percentage)
    return Math.round(percentage)
  }

}
