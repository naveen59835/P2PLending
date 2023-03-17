import { Component, OnInit } from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";

@Component({
  selector: 'app-borrower-dashboard',
  templateUrl: './borrower-dashboard.component.html',
  styleUrls: ['./borrower-dashboard.component.css']
})
export class BorrowerDashboardComponent implements OnInit {
  loans:any=[
    { id: 1, lender: 'ABC Bank', amount: 10000, disbursedOn: new Date(2022, 0, 1), repaidOn: new Date(2022, 6, 1) },
    { id: 2, lender: 'XYZ Bank', amount: 15000, disbursedOn: new Date(2022, 1, 1), repaidOn: new Date(2022, 8, 1) },
  ];
  constructor() { }

  ngOnInit(): void {
    //call the loan microservice and assign the data to loans variable
  }

}
