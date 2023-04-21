import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Borrower} from "../model/Borrower";
import {MatSnackBar} from "@angular/material/snack-bar";
import {LoginService} from "../service/login.service";
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginForm: FormGroup;
  hide = true;
  constructor(private fb: FormBuilder, private _snackBar: MatSnackBar, private loginService : LoginService,private router: Router) {
    this.loginForm = this.fb.group({
      email: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required, Validators.minLength(8), Validators.pattern(/^(?=.*\d)(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z]).{8,}$/)]),
      role : new FormControl("borrower",[Validators.required])
    });
  }

  get password() {
    return this.loginForm.get("password");
  }

  get email() {
    return this.loginForm.get("phone");
  }

  openSnack(message : string, action : string) {

    this._snackBar.open(message, action, {
      duration: 5000,
      panelClass: ['mat-toolbar', 'mat-primary']
    })

  }


  onSubmit() {
    if(this.loginForm.valid){
      this.loginService.login(this.loginForm.value).subscribe({
        next : (data:any)=>{
          this.openSnack("Logged in successfully","Success");
          if(data){
            window.localStorage.setItem("userName",data.name);
            window.localStorage.setItem("email",data.email);
            window.localStorage.setItem("token",data.token);
            window.localStorage.setItem("role",data.role)
          }
          this.loginForm.reset();


            this.router.navigate(['/dashboard'])

        },
        error : (err)=> this.openSnack("Wrong email or password","Failure")
      })
    }
    else{
      this.openSnack("Couldn't Submit the form","Failure");
    }

  }

}
