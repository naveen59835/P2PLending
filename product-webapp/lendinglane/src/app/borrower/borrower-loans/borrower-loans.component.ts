import { Component, OnInit } from '@angular/core';
import {MatDialog, MatDialogRef} from "@angular/material/dialog";
import {LoanDialogComponent} from "./loan-dialog/loan-dialog.component";

@Component({
  selector: 'app-borrower-loans',
  templateUrl: './borrower-loans.component.html',
  styleUrls: ['./borrower-loans.component.css']
})
export class BorrowerLoansComponent implements OnInit {

  constructor(private dialog: MatDialog) {
  }

  data = [{}, {}, {}]

  ngOnInit(): void {
  }

  openLoanDialog() {
    this.dialog.open(LoanDialogComponent, {
      width: '300px'
    })
  }
}
