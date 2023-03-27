import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SignupComponent } from './signup/signup.component';
import {LoginComponent} from "./login/login.component";
import {DashboardComponent} from "./dashboard/dashboard.component";
import {DashboardProfileComponent} from "./dashboard/dashboard-profile/dashboard-profile.component";
import {DashboardHomeComponent} from "./dashboard/dashboard-home/dashboard-home.component";
import {DashboardTransactionComponent} from "./dashboard/dashboard-transaction/dashboard-transaction.component";
import {DashboardLoansComponent} from "./dashboard/dashboard-loans/dashboard-loans.component";
import {LoanDetailsComponent} from "./borrower/borrower-loans/loan-details/loan-details.component";
import {DashboardMessagesComponent} from "./dashboard/dashboard-messages/dashboard-messages.component";
import {DashboardMessageWindowComponent} from "./dashboard/dashboard-message-window/dashboard-message-window.component";

const routes: Routes = [
  {path:'', pathMatch:"full",redirectTo:'signup'},
  {path:'signup',component:SignupComponent},
  {path:'login',component:LoginComponent},
  {
    path:'dashboard',
    component : DashboardComponent,
    //Add can activate to check if the user is authenticated
    children:[
      {path:'',component:DashboardHomeComponent},
      {path:'profile',component:DashboardProfileComponent},
      {path:'transactions',component:DashboardTransactionComponent},
      {path:'loan',component:DashboardLoansComponent},
      {path:'loan/:id',component:LoanDetailsComponent},
      {path:"messages",component:DashboardMessagesComponent},
      {path:"messages/:id",component:DashboardMessageWindowComponent}
    ]
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
