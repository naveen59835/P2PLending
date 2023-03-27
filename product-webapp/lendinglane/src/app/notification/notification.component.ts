import { Component, Input, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.css']
})
export class NotificationComponent {
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

  constructor(private dialogRef: MatDialogRef<NotificationComponent>) {}

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
