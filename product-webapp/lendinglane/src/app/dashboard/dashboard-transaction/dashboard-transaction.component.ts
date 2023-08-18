import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-dashboard-transaction',
  templateUrl: './dashboard-transaction.component.html',
  styleUrls: ['./dashboard-transaction.component.css']
})
export class DashboardTransactionComponent implements OnInit {

  role = localStorage.getItem("role");
  constructor() { }

  ngOnInit(): void {
  }

}
