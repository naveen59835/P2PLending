import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import * as http from "http";
import {LoanService} from "../../../service/loan.service";
import {FormBuilder, Validators} from "@angular/forms";
import {MatDialogRef} from "@angular/material/dialog";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-loan-dialog',
  templateUrl: './loan-dialog.component.html',
  styleUrls: ['./loan-dialog.component.css']
})
export class LoanDialogComponent {
  @Output()dialogClose!:EventEmitter<any>;
  constructor(private  loanService : LoanService, private fb : FormBuilder, private dialog : MatDialogRef<LoanDialogComponent>, private snackBar : MatSnackBar) { }
  loanForm = this.fb.group({
    amount : this.fb.control("",[Validators.required,Validators.minLength(4),Validators.pattern(/^[0-9]+$/)]),
    terms : this.fb.control("",[Validators.required]),
    termsAndConditions: this.fb.control(false,[Validators.requiredTrue])
    }
  )
  applyLoan(){
    if(this.loanForm.valid){
      let amount = this.loanForm.get("amount")?.value;
      let terms = this.loanForm.get("terms")?.value;
      this.loanService.applyLoan({"amount":amount,"terms":terms}).subscribe({
        next : (data) => {
          this.dialog.close()
          this.snackBar.open("Loan Added Successfully","Success",{duration:3000})
        },
        error :(err) => {
          this.dialog.close();
          this.snackBar.open(err.error,"Failure",{duration:3000})
        }
      })

    }
  }

}
