import { Component, OnInit } from '@angular/core';
import {SidenavService} from "../../service/sidenav.service";

@Component({
  selector: 'app-dashboard-home',
  templateUrl: './dashboard-home.component.html',
  styleUrls: ['./dashboard-home.component.css']
})
export class DashboardHomeComponent implements OnInit {
  window!:Window;
  constructor() {
    this.window = window;
  }

  ngOnInit(): void {
  }

}
