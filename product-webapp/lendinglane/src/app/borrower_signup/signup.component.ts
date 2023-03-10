import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {AbstractControl, FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {MatSnackBar} from '@angular/material/snack-bar';
import {Borrower} from '../model/Borrower';
import { BorrowerRegistrationService } from '../service/borrower-registration.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
  
  hide = true;
  emailFormControl = new FormControl('', [Validators.required, Validators.email]);
  profileForm: FormGroup;
  empl: Borrower | undefined;
  employee = {} as Borrower;
  formGroup: any;

  constructor(private fb: FormBuilder, private _snackBar: MatSnackBar,private borrower:BorrowerRegistrationService) {
    this.profileForm = this.fb.group({

      firstName: new FormControl('', [Validators.required, Validators.minLength(3)]),
      lastName: new FormControl('', [Validators.required, Validators.minLength(3)]),
      emailId:new FormControl('',[Validators.required, Validators.pattern(/^[a-zA-Z0-9.! #$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*\.com$/)]),
      phoneNo: new FormControl('', [Validators.required, Validators.pattern(/^[7-9]\d{9}$/), Validators.minLength(10)]),
      password: new FormControl('', [Validators.required, Validators.minLength(8), Validators.pattern(/^(?=.*\d)(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z]).{8,}$/)]),
      confirmPassword: new FormControl('', [Validators.required, Validators.minLength(8)]),
    },{validator:[this.passwordShouldMatchValidation]});
  }

  get firstName() {
    return this.profileForm.get("firstName");
  }
  get lastName() {
    return this.profileForm.get("lastName");
  }

  get emailId() {
    return this.profileForm.get("emailId");
  }

  get password() {
    return this.profileForm.get("password");
  }

  get confirmPassword() {
    return this.profileForm.get("confirmPassword");
  }


  get phoneNo() {
    return this.profileForm.get("phoneNo");
  }


  ngOnInit(): void {
  }



  openSnack(message : string, action : string) {

    this._snackBar.open(message, action, {
      duration: 5000,
      panelClass: ['mat-toolbar', 'mat-primary']
    })

  }

  passwordShouldMatchValidation(myControl:AbstractControl){
    const passwordValue=myControl.get('password')?.value;
    const confirmPasswordValue=myControl.get('confirmPassword')?.value;
    if(!passwordValue || !confirmPasswordValue){
      return null;
   }
  if(passwordValue != confirmPasswordValue){
    return {passwordShouldMatch : false};
 }
 return null;
}



  onSubmit() {

    this.borrower.createBorrower(this.profileForm.value).subscribe(
      (data) => {
        console.log(data);
        if(this.profileForm.valid){
            this.openSnack("Form Submitted Successfully","Success");
            this.profileForm.reset();
          }
          else{
            this.openSnack("Couldn't Submit the form","Failure");
          }

      },
      error=>console.log(error)
    );

    }
}

