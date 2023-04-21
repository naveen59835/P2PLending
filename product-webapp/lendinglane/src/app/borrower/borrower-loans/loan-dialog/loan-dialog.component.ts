import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {LoanService} from "../../../service/loan.service";
import {AbstractControl, FormBuilder, ValidatorFn, Validators} from "@angular/forms";
import {MatDialogRef} from "@angular/material/dialog";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Router} from "@angular/router";

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
    amount : this.fb.control('',[Validators.required,Validators.minLength(4),Validators.pattern(/^[0-9]+$/),this.loanRangeValidation()]),
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

  loanRangeValidation () :ValidatorFn{
    return  (control: AbstractControl): { [key: string]: any } | null => {
      let amount = parseInt(control.value);
      if (amount<1000 || amount>200000)
      return {"invalidAmount":"Amount must be within 1000 - 200000"}
      return null;
    }
  }

  click()
  {
    this.route.navigate(['/dashboard/term'])
    this.dialog.close();

  }
  interestRates :any = {
    "0" : 0,
    "3" : 16,
    "6" : 17,
    "12" : 19,
    "24" : 20,
  }
  get totalAmount(){
    let amount = parseInt(this.loanForm.get("amount")?.value || "0");
    let interestRate = this.interestRates[this.loanForm.get("terms")?.value || 0]
    return amount+(amount*(interestRate*0.01));
  }

  closeDialog(){
    this.dialog.close();
  }
}
