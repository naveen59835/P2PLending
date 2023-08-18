import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {MatSnackBar} from '@angular/material/snack-bar';
import {Borrower} from '../model/Borrower';
import {BorrowerRegistrationService} from '../service/borrower-registration.service';
import {RegistrationService} from "../service/registration.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
  passwordHide = true;
  confirmPasswordHide = true;
  emailFormControl = new FormControl('', [Validators.required, Validators.email]);
  profileForm: FormGroup;
  empl: Borrower | undefined;
  employee = {} as Borrower;
  formGroup: any;

  constructor(private router : Router, private fb: FormBuilder, private _snackBar: MatSnackBar, private borrowerService: RegistrationService) {
    this.profileForm = this.fb.group({

      firstName: new FormControl('', [Validators.required, Validators.minLength(3)]),
      lastName: new FormControl('', [Validators.required, Validators.minLength(3)]),
      emailId: new FormControl('', [Validators.required, Validators.pattern(/^[a-zA-Z0-9.! #$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*\.com$/)]),
      password: new FormControl('', [Validators.required, Validators.minLength(8), Validators.pattern(/^(?=.*\d)(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z]).{8,}$/)]),
      confirmPassword: new FormControl('', [Validators.required, Validators.minLength(8)]),
      role: fb.control('', [Validators.required])
    }, {validator: [this.passwordShouldMatchValidation]});
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


  openSnack(message: string, action: string) {

    this._snackBar.open(message, action, {
      duration: 5000,
      panelClass: ['mat-toolbar', 'mat-primary']
    })

  }

  passwordShouldMatchValidation(myControl: AbstractControl) {
    const passwordValue = myControl.get('password')?.value;
    const confirmPasswordValue = myControl.get('confirmPassword')?.value;
    if (!passwordValue || !confirmPasswordValue) {
      return null;
    }
    if (passwordValue != confirmPasswordValue) {
      return {passwordShouldMatch: false};
    }
    return null;
  }


  onSubmit() {
    this.borrowerService.registerUser(this.profileForm.value).subscribe({
      next :(data) => {
        if (this.profileForm.valid) {
          this.openSnack("Form Submitted Successfully", "Success");
          this.profileForm.reset();
          this.router.navigateByUrl("/login");
        } else {
          this.openSnack("Couldn't Submit the form", "Failure");
        }
      },
      error : error => console.log(error)
      }
    );

  }
}

