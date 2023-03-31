import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-dashboard-message-window',
  templateUrl: './dashboard-message-window.component.html',
  styleUrls: ['./dashboard-message-window.component.css']
})
export class DashboardMessageWindowComponent implements OnInit {

    role = localStorage.getItem("role");
    constructor() { }

  ngOnInit(): void {
  }

}
