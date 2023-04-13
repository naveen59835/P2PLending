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
    this.breakpointObserver.observe([Breakpoints.Small,Breakpoints.XSmall]).pipe(
      map(result=>result.matches)
    ).subscribe({
        next : (data)=>{
          this.isSmall = data;
          this.sidenavService.isSidenavOpened=data
          this.sidenavService.isSmallScreen=data
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
