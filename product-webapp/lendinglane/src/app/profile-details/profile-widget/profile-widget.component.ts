import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-profile-widget',
  templateUrl: './profile-widget.component.html',
  styleUrls: ['./profile-widget.component.css']
})
export class ProfileWidgetComponent implements OnInit {

  @Input() profileWidgetData :any;
  constructor() { }

  ngOnInit(): void {
  }
  get name(){
    return localStorage.getItem("userName") || ""
  }
  get role(){
    return localStorage.getItem("role");
  }
}
