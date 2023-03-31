import { Component, OnInit } from '@angular/core';
import {ChatService} from "../../service/chat.service";

@Component({
  selector: 'app-lender-messages',
  templateUrl: './lender-messages.component.html',
  styleUrls: ['./lender-messages.component.css']
})
export class LenderMessagesComponent implements OnInit {

  messages :any =[];
  activeMessage : any =[{},{}];
  finishedMessage :any =[]
  constructor(private chatService : ChatService) { }

  ngOnInit(): void {
    this.chatService.getChats().subscribe({
      next : (data)=> {
        this.messages = data
        console.log(data)
      }
    })
  }

}
