import { Component, Input, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import {NotificationService} from "../service/notification.service";

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.css']
})
export class NotificationComponent implements OnInit{
  @Input() isOpen = false;
  @Input() title = 'All Notifications';
  @Input() notifications: Array<any> =  [
    {
      lender: 'Lender 1',
      message: 'You have a new loan request',

    },
    {
      lender: 'Lender 2',
      message: 'Your loan application has been approved',

    },
    {
      lender: 'Lender 3',
      message: 'You have received a payment',
    }
  ];
  ngOnInit() {
   this.notificationService.getAllNotifications().subscribe({
     next : (data:any) => {
       data.sort((data1:any,data2:any)=>new Date(data2.timeStamp).valueOf() - new Date(data1.timeStamp).valueOf())
       this.notifications = data
     }
   })
  }

  constructor(private dialogRef: MatDialogRef<NotificationComponent>, private notificationService:NotificationService) {}

  closeNotification() {
    this.dialogRef.close();
  }

  clearAllNotifications() {
    this.notifications = [];
  }

  clearNotification(notification: any) {
    const index = this.notifications.indexOf(notification);
    if (index > -1) {
      this.notifications.splice(index, 1);
    }
  }
}
