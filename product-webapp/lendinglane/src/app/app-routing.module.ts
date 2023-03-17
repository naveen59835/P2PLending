import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SignupComponent } from './signup/signup.component';
import {LoginComponent} from "./login/login.component";
import {DashboardComponent} from "./dashboard/dashboard.component";
import {DashboardProfileComponent} from "./dashboard/dashboard-profile/dashboard-profile.component";
import {DashboardHomeComponent} from "./dashboard/dashboard-home/dashboard-home.component";
import { LenderAddressDetailComponent } from './lender/lender-address-detail/lender-address-detail.component';
import {DashboardTransactionComponent} from "./dashboard/dashboard-transaction/dashboard-transaction.component";
import {DashboardLoansComponent} from "./dashboard/dashboard-loans/dashboard-loans.component";

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
      {path:'lenderaddress',component:LenderAddressDetailComponent},
      {path:'transactions',component:DashboardTransactionComponent},
      {path:'loan',component:DashboardLoansComponent}
    ]
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
