import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-dashboard-profile',
  templateUrl: './dashboard-profile.component.html',
  styleUrls: ['./dashboard-profile.component.css']
})
export class DashboardProfileComponent implements OnInit {
  role:string =""
  constructor() { }

  ngOnInit(): void {
    this.role = localStorage.getItem("role") || "";
  }

}
