import { Component, OnInit } from '@angular/core';
import {SidenavService} from "../service/sidenav.service";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  constructor(private sidenavService:SidenavService) { }
  get isSidenavOpened() {
    return this.sidenavService.isSidenavOpened;
  }
  get isSmallScreen(){
    return this.sidenavService.isSmallScreen;
  }
  ngOnInit(): void {
  }

}
