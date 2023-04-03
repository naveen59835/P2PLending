import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, NgForm, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent  {
  contactForm: FormGroup;

  constructor(private formBuilder: FormBuilder, private router: Router, private http: HttpClient,private _snackBar: MatSnackBar) {
    this.contactForm = this.formBuilder.group({
      email: ['', [Validators.required]],
      message: ['', [Validators.required]]
    });
  }

  get email(){
    return this.contactForm.get("email");
  }
  get message(){
    return this.contactForm.get("message");
  }


  onSubmit() {
    this._snackBar.open('We Will Contact You Soon', 'Thankyou!', {
      duration: 5000,
      panelClass: ['mat-toolbar', 'mat-primary']
    });

    const formData = this.contactForm.value;
    console.log(formData);
    console.log(this.contactForm.value);

    this.http.post('http://localhost:8089/api/v1/contactus', formData).subscribe(
      (response) => console.log(response),
      (error) => console.log(error)
    );

    this.contactForm.reset();
  }


  goto() {
    this.router.navigate(['/signup']);
  }


}
