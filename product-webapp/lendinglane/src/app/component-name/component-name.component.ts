
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormGroup, FormBuilder, Validators, AbstractControl, FormControl } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Borrower } from '../model/Borrower';


@Component({
  selector: 'app-component-name',
  templateUrl: './component-name.component.html',
  styleUrls: ['./component-name.component.css']
})
export class ComponentNameComponent  {

  emailFormControl = new FormControl('', [Validators.required, Validators.email]);
  profileForm:FormGroup;
  empl:Borrower|undefined;
  employee = {} as Borrower;




  constructor (private fb: FormBuilder,private _snackBar: MatSnackBar){
    this.profileForm = this.fb.group({

      name:new FormControl('',[Validators.required,Validators.minLength(3)]),
      email:new FormControl('',[Validators.required ,Validators.required, Validators.pattern(/^[a-zA-Z0-9.! #$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*\.com$/)]),
      phoneNo:new FormControl('',[Validators.required,Validators.pattern(/^[7-9]\d{9}$/),Validators.minLength(10)]),
      password:new FormControl('',[Validators.required,Validators.minLength(8),Validators.pattern(/^(?=.*\d)(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z]).{8,}$/)]),
      userId:new FormControl('',[Validators.required,Validators.minLength(3)]),



    },{Validators:[this.checkIfEmailsAreValid]});
   }



  openSnack() {

    this._snackBar.open('Feedback submitted successfully', 'success', {​
      duration: 5000,​
      panelClass: ['mat-toolbar', 'mat-primary']​
      })

  }
  get name(){
    return this.profileForm.get("name");
  }

  get email(){
    return this.profileForm.get("email");
  }
  get password(){
    return this.profileForm.get("password");
  }
  get userId(){
    return this.profileForm.get("userId");
  }


  get phoneNo(){
    return this.profileForm.get("phoneNo");
  }


  @Output()
  addEmployee:EventEmitter<Borrower>=new EventEmitter<Borrower>();

  user={

  }







  checkIfEmailsAreValid(c: AbstractControl) {
    if (c.value !== '') {
      const emailString = c.value;
      const emails = emailString.split(',').map((e: string) => e.trim());
      const emailRegex = /^(([^<>()[\]\.,;:\s@\"]+(\.[^<>()[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,}\.com)$/i;
      const anyInvalidEmail = emails.every((e: string) => e.match(emailRegex) !== null);
      if (!anyInvalidEmail) {
        return { checkIfGuestEmailsAreValid: false }
      }
    }
    return null;
  }




  onSubmit() {
    console.log(this.profileForm.value);
    this.employee = this.profileForm.value;
    this.addEmployee.emit(this.employee);
    this.openSnack();
    this.profileForm.reset();
    this.profileForm.markAsUntouched();
    this.profileForm.markAsPristine();
    this.profileForm.markAsPending();
    this.profileForm.updateValueAndValidity();

  }








}
