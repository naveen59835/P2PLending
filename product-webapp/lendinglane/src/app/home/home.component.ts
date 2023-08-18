import { HttpClient } from '@angular/common/http';
import {AfterViewInit, Component, OnInit} from '@angular/core';
import { FormBuilder, FormControl, FormGroup, NgForm, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import {SidenavService} from "../service/sidenav.service";
import {LoginService} from "../service/login.service";
import {PaymentService} from "../service/payment.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements AfterViewInit {
  contactForm: FormGroup;
  totalUsers=0;
  totalMoney=0;
  moneyThisMonth=0

  images =[{path : "https://images.unsplash.com/photo-1621317849220-af499f2180d4?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1091&q=80"},
    {path : "https://images.unsplash.com/photo-1681069693462-0e5f5db2393e?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80"},
    {path : "https://images.unsplash.com/photo-1681069693462-0e5f5db2393e?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80"},
  ]
  constructor(private paymentService : PaymentService, private authService : LoginService,private formBuilder: FormBuilder, private router: Router, private http: HttpClient,private _snackBar: MatSnackBar,private sidenav : SidenavService) {
    this.contactForm = this.formBuilder.group({
      email: ['', [Validators.required]],
      name: ['', [Validators.required]],
      phone: ['', [Validators.required]],
      message: ['', [Validators.required]]
    });
  }

  get email(){
    return this.contactForm.get("email");
  }
  get message(){
    return this.contactForm.get("message");
  }
  get phone(){
    return this.contactForm.get("phone");
  }
  get name(){
    return this.contactForm.get("name");
  }



  onSubmit() {
    this._snackBar.open('We Will Contact You Soon', 'Thankyou!', {
      duration: 5000,
      panelClass: ['mat-toolbar', 'mat-primary']
    });

    const formData = this.contactForm.value;
    console.log(formData);
    console.log(this.contactForm.value);

    this.http.post('http://localhost:9002/api/v1/notification/reachus', formData).subscribe(
      (response) => console.log(response),
      (error) => console.log(error)
    );

    this.http.post('http://localhost:9002/api/v1/notification/contactus', formData).subscribe(
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
  get isSmallScreen(){
   return this.sidenav.isSmallScreen;
  }
  getTotalUsers(){
    this.authService.getTotalUsers().subscribe({
      next : (data:any) => this.totalUsers = data.totalUsers,
      error : ()=>this.totalUsers=0
    })
  }
  getTotalMoneyInvested(){
    this.paymentService.totalPayments().subscribe({
      next : (data:any) => this.totalMoney = data.total,
      error : ()=>this.totalMoney=0
    })
  }

  ngAfterViewInit(): void {
    this.getTotalUsers();
    this.getTotalMoneyInvested()
  }
}
