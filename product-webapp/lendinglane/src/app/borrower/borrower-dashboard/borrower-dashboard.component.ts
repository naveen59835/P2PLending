import { Component, OnInit } from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {LoanService} from "../../service/loan.service";
import {SidenavService} from "../../service/sidenav.service";

@Component({
  selector: 'app-borrower-dashboard',
  templateUrl: './borrower-dashboard.component.html',
  styleUrls: ['./borrower-dashboard.component.css']
})
export class BorrowerDashboardComponent implements OnInit {
  loans:Array<any>=[];
  constructor(private loanService : LoanService, private sideNav : SidenavService) { }

  ngOnInit(): void {
    let id = localStorage.getItem("email") || "";
    let role = localStorage.getItem("role") || "";
    this.loanService.getAllLoans(id,role).subscribe({
      next : (data:any)=> this.loans = data
    })
  }
  totalPaid(emi : Array<any>){
    if(emi.length>0)
    return Math.round(emi.reduce((acc,current)=>current.paymentStatus?acc+current.price:acc+0,0) * 10) / 10
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
    return Math.round(percentage)
  }
  get isMobile(){
    return this.sideNav.isMobile;
  }

  get isIpad(){
    return this.sideNav.isIpad
  }

}
