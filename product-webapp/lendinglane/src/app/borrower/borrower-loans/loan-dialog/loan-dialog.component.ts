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
  disable:boolean=true;

  @Output()dialogClose!:EventEmitter<any>;
  constructor(private  loanService : LoanService, private fb : FormBuilder, private dialog : MatDialogRef<LoanDialogComponent>, private snackBar : MatSnackBar,private route:Router) { 


    if(loanService.valid==true){

      this.disable=false
    }
    else{
 
      this.disable=true
    }
  }



  loanForm = this.fb.group({
    amount : this.fb.control('',[Validators.required,Validators.minLength(4),Validators.pattern(/^[0-9]+$/)]),
    terms : this.fb.control('',[Validators.required]),
    termsAndConditions: this.fb.control(this.loanService.valid,[Validators.requiredTrue])
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


  click()
  {
    this.route.navigate(['/dashboard/term'])
    this.dialog.close();
   
  }
}
