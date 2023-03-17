import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-dashboard-loans',
  templateUrl: './dashboard-loans.component.html',
  styleUrls: ['./dashboard-loans.component.css']
})
export class DashboardLoansComponent implements OnInit {
  role = localStorage.getItem("role");
  constructor() { }

  ngOnInit(): void {
  }

}
