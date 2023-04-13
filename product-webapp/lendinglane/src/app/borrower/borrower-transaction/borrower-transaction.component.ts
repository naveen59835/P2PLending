import { Component, OnInit } from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {PaymentService} from "../../service/payment.service";

@Component({
  selector: 'app-borrower-transaction',
  templateUrl: './borrower-transaction.component.html',
  styleUrls: ['./borrower-transaction.component.css']
})
export class BorrowerTransactionComponent implements OnInit {

  constructor(private paymentService : PaymentService) { }
  displayedColumns: string[] = ['#', 'amount', 'type', 'remarks', 'date'];
  transactionData:any

  dataSource = new MatTableDataSource([
    {id: 1, amount: 1000, type: 'debit', remarks: 'Payment received', date: new Date('2022-03-15')},
    {id: 2, amount: 500, type: 'credit', remarks: 'Refund issued', date: new Date('2022-03-10')},
    {id: 3, amount: 750, type: 'debit', remarks: 'Payment received', date: new Date('2022-03-05')},
    {id: 4, amount: 250, type: 'credit', remarks: 'Refund issued', date: new Date('2022-03-01')}
  ]);

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

}
