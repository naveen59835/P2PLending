import { Component, OnInit } from '@angular/core';
import {ChatService} from "../../service/chat.service";
declare let SockJS : any
import {Stomp} from "@stomp/stompjs";
import {SidenavService} from "../../service/sidenav.service";

@Component({
  selector: 'app-borrower-messages',
  templateUrl: './borrower-messages.component.html',
  styleUrls: ['./borrower-messages.component.css']
})
export class BorrowerMessagesComponent implements OnInit {

  messages :any =[];
  activeMessage : any =[{},{}];
  finishedMessage :any =[]
  constructor(private chatService : ChatService, private sideNav : SidenavService) { }

  ngOnInit(): void {
    this.chatService.getChats().subscribe({
      next : (data)=> {
        this.messages = data
        console.log(data)
      }
    })
  }
  get isSmallScreen(){
    return this.sideNav.isSmallScreen;
  }

}
