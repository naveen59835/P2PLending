import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-dashboard-messages',
  templateUrl: './dashboard-messages.component.html',
  styleUrls: ['./dashboard-messages.component.css']
})
export class DashboardMessagesComponent implements OnInit {

  role=""
  constructor() { }

  ngOnInit(): void {
    this.role = localStorage.getItem("role") || ""
  }

}
