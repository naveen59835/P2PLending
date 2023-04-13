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
  images =[{path : "https://images.unsplash.com/photo-1621317849220-af499f2180d4?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1091&q=80"},
    {path : "https://images.unsplash.com/photo-1681069693462-0e5f5db2393e?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80"},
    {path : "https://images.unsplash.com/photo-1681069693462-0e5f5db2393e?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80"},
  ]
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

    this.http.post('http://localhost:9002/api/v1/contactus', formData).subscribe(
      (response) => console.log(response),
      (error) => console.log(error)
    );

    this.contactForm.reset();
  }


  goto() {
    this.router.navigate(['/signup']);
  }
  print(event:any){
    console.log(event)
  }

}
