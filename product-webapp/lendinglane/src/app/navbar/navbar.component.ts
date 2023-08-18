import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { LoginService } from './../service/login.service';
import { Component, OnInit } from '@angular/core';
import {NotificationComponent} from "../notification/notification.component";
import {SidenavService} from "../service/sidenav.service";
import {BreakpointObserver, Breakpoints} from "@angular/cdk/layout";
import {map} from "rxjs";
// import { NotificationComponent } from '../notification/notification.component';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor(public authservice:LoginService,private router:Router,private dialog:MatDialog,private sidenavService : SidenavService, private breakpointObserver : BreakpointObserver) { }

  ngOnInit(): void {
    this.breakpointObserver.observe([Breakpoints.Small,Breakpoints.XSmall]).subscribe({
        next : (data)=>{
          this.isSmall = data.matches;
          this.sidenavService.isSidenavOpened=this.isSmall
          this.sidenavService.isSmallScreen=this.isSmall
          this.sidenavService.isMobile = data.breakpoints[Breakpoints.XSmall]
          this.sidenavService.isIpad = data.breakpoints[Breakpoints.Small]
        }
      }
    )
  }

  logout() {
      this.authservice.logout();

  }
  openNotification() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.position = { top: '80px', right: '20px' };
    this.dialog.open(NotificationComponent, dialogConfig);
  }
  isSmall=false;
  toggleSidenav(){
    this.sidenavService.isSidenavOpened=!this.sidenavService.isSidenavOpened;
  }





}
