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

const routes: Routes = [
  {path:'', pathMatch:"full",redirectTo:'signup'},
  {path:'signup',component:SignupComponent,canActivate: [AuthGuardGuard]},
  {path:'login',component:LoginComponent,canActivate: [AuthGuardGuard]},
  {
    path:'dashboard',
    component : DashboardComponent,
    canActivate: [DashboardAuthGuardGuard],
    //Add can activate to check if the user is authenticated
    children:[
      {path:'',component:DashboardHomeComponent},
      {path:'profile',component:DashboardProfileComponent},
      {path:'transactions',component:DashboardTransactionComponent},
      {path:'loan',component:DashboardLoansComponent},
      {path:'loan/:id',component:LoanDetailsComponent},
    ]
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
