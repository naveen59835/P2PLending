import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SignupComponent } from './signup/signup.component';
import {LoginComponent} from "./login/login.component";
import {DashboardComponent} from "./dashboard/dashboard.component";
import {DashboardProfileComponent} from "./dashboard/dashboard-profile/dashboard-profile.component";
import {DashboardHomeComponent} from "./dashboard/dashboard-home/dashboard-home.component";
import {DashboardTransactionComponent} from "./dashboard/dashboard-transaction/dashboard-transaction.component";
import {DashboardLoansComponent} from "./dashboard/dashboard-loans/dashboard-loans.component";
import { AuthGuardGuard } from './guards/auth-guard.guard';
import {LoanDetailsComponent} from "./borrower/borrower-loans/loan-details/loan-details.component";
import { TermconditionComponent } from './termcondition/termcondition.component';
import { PagenotfoundComponent } from './pagenotfound/pagenotfound.component';
import { DashboardAuthGuardGuard } from './guards/dashboard-auth-guard.guard';
import { RecommendedborrowerComponent } from './lender/recommendedborrower/recommendedborrower.component';
import {DashboardMessagesComponent} from "./dashboard/dashboard-messages/dashboard-messages.component";
import {DashboardMessageWindowComponent} from "./dashboard/dashboard-message-window/dashboard-message-window.component";
import { HomeComponent } from './home/home.component';
import {DashboardLoanDetailsComponent} from "./dashboard/dashboard-loan-details/dashboard-loan-details.component";


const routes: Routes = [
  {path:'', pathMatch:"full",redirectTo:'home'},
  {path:'signup',component:SignupComponent,canActivate: [AuthGuardGuard]},
  {path:'login',component:LoginComponent,canActivate: [AuthGuardGuard]},
  {path:'home',component:HomeComponent},
  {
    path:'dashboard',
    component : DashboardComponent,
    canActivate: [DashboardAuthGuardGuard],

    children:[
      {path:'',component:DashboardHomeComponent},
      {path:'profile',component:DashboardProfileComponent},
      {path:'transactions',component:DashboardTransactionComponent},
      {path:'loan',component:DashboardLoansComponent},
      {path:'loan/:id',component:DashboardLoanDetailsComponent},
      {path:'term',component:TermconditionComponent},
      {path:'recommendedborrowerdetail/:id',component:RecommendedborrowerComponent},
      {path:"chat",component:DashboardMessagesComponent},
      {path:"chat/:id",component:DashboardMessageWindowComponent},
    ]
  },
  {path:'**',pathMatch:'full',redirectTo:'page-not-found'},
  {path:"page-not-found",component:PagenotfoundComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
