import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Message} from "../model/Message";
@Injectable({
  providedIn: 'root'
})
export class ChatService {

  constructor(private http : HttpClient) { }

  public getChats(){
    let email = localStorage.getItem("email");
    let role = localStorage.getItem("role")
    return this.http.get(`http://localhost:8085/api/v1/chat/chats?email=${email}&role=${role}`)
  }
  public getMessages(id : any){
    return this.http.get(`http://localhost:8085/api/v1/chat/chats/${id}`)
  }

  public getSortedChat(messages:Array<Message>){
   return messages.sort((message1:Message,message2:Message)=>new Date(message1.timeStamp).valueOf() - new Date(message1.timeStamp).valueOf())
  }

}
