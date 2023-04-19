import { Component, OnInit } from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {PaymentService} from "../../service/payment.service";
import {SidenavService} from "../../service/sidenav.service";

@Component({
  selector: 'app-borrower-transaction',
  templateUrl: './borrower-transaction.component.html',
  styleUrls: ['./borrower-transaction.component.css']
})
export class BorrowerTransactionComponent implements OnInit {

  constructor(private paymentService : PaymentService, private sideNav : SidenavService) { }
  displayedColumns: string[] = ['#', 'amount', 'type', 'remarks', 'date'];
  transactionData:any

  dataSource = new MatTableDataSource();

  ngOnInit(): void {
    //Gather data from payment microservice and populate dataSource
    this.paymentService.getALlPayment().subscribe({
      next:(data:any)=> {
        this.transactionData = data
        this.dataSource = new MatTableDataSource(this.addType(this.paymentService.sortPayment(data)));
      }
    })
  }
  addType(paymentData :Array<any>){
    for (let data of paymentData) {
      if(data.fromAccount === localStorage.getItem("email")){
        data.type = "Debit"
      }else  data.type = "Credit"
    }
    return paymentData;
  }
  changeTransactionType(event :any){
    if(event === "all"){
      this.dataSource = new MatTableDataSource(this.addType(this.paymentService.sortPayment(this.transactionData)));
    }
    else{
      let filteredTransactions = this.transactionData.filter((transaction:any)=>transaction.type.includes(event))
      this.dataSource = new MatTableDataSource(this.addType(this.paymentService.sortPayment(filteredTransactions)));
    }
  }
  get isSmall(){
    return this.sideNav.isSmallScreen;
  }

}
