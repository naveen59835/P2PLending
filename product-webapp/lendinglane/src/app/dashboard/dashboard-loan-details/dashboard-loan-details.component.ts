import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-dashboard-loan-details',
  templateUrl: './dashboard-loan-details.component.html',
  styleUrls: ['./dashboard-loan-details.component.css']
})
export class DashboardLoanDetailsComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

  get role(){
    return window.localStorage.getItem("role") || ""
   }

}
