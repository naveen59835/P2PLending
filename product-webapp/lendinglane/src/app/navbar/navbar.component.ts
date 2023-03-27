import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { LoginService } from './../service/login.service';
import { Component, OnInit } from '@angular/core';
// import { NotificationComponent } from '../notification/notification.component';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor(public authservice:LoginService,private router:Router,private dialog:MatDialog) { }

  ngOnInit(): void {
  }

  logout() {
      this.authservice.logout();

  }
  openNotification() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.position = { top: '80px', right: '20px' };

    // this.dialog.open(NotificationComponent, dialogConfig);
  }





}
