import {AfterViewChecked, AfterViewInit, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {Stomp} from "@stomp/stompjs";
import {ActivatedRoute, Router} from "@angular/router";
import {ChatService} from "../../service/chat.service";
import {FormBuilder, Validators} from "@angular/forms";
import {Message} from "../../model/Message";
declare let SockJS : any;
@Component({
  selector: 'app-lender-message-window',
  templateUrl: './lender-message-window.component.html',
  styleUrls: ['./lender-message-window.component.css']
})
export class LenderMessageWindowComponent implements OnInit,AfterViewChecked{

  messages :Array<Message> =[]
  stompClient:any;
  @ViewChild("chatContainer") chatContainer !: ElementRef<any>;
  constructor(private router : Router,private activatedRoute : ActivatedRoute, private chatService : ChatService, private fb: FormBuilder) { }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe({
      next :(data:any) => this.getMessages(data.id)
    })
  }
  ngAfterViewChecked() {
    this.chatContainer.nativeElement.scrollTop =this.chatContainer.nativeElement.scrollHeight+(this.messages.length*20);
  }

  textForm = this.fb.group({
    message: ['',Validators.required],
    role: [localStorage.getItem("role")],
    recipientId: ['',Validators.required],
    senderId: [localStorage.getItem("email")]
  });
  getMessages(id : any){
    this.chatService.getMessages(id).subscribe({
      next : (data:any) =>{this.messages =data;
        this.messages = this.chatService.getSortedChat(data.messages);
        this.textForm.get("recipientId")?.setValue(data.borrowerId);
        this.initConnection()
      },
      error : ()=> this.router.navigateByUrl("/not-found")
    })

  }
  createChat(id:any){

  }
  initConnection(){
    let ws = new SockJS("http://localhost:8085/chat")
    this.stompClient = Stomp.over(ws)
    let that = this;
    this.stompClient.connect({},()=>{
      that.stompClient.subscribe("/topic/messages/"+localStorage.getItem("email"), (msg:any)=>{
        this.messages.push(JSON.parse(msg.body));
        this.chatContainer.nativeElement.scrollTop = this.chatContainer.nativeElement.scrollHeight+(this.messages.length*20);
      })
    })
  }
  sendMessage(){
    let message:Message ={};
    message.message = this.textForm.get("message")?.value ||"";
    message.recipientId = this.textForm.get("recipientId")?.value ||"";
    message.senderId = this.textForm.get("senderId")?.value ||"";
    message.timeStamp = new Date();
    if(this.textForm.valid){
      this.messages.push(message);
      this.stompClient.send("/app/send",{},JSON.stringify(this.textForm.value));
      this.textForm.get("message")?.setValue("");
      this.chatContainer.nativeElement.scrollTop = this.chatContainer.nativeElement.scrollHeight+(this.messages.length*20);
    }
  }
  isSender(message : Message){
    if(message.senderId === localStorage.getItem("email"))
      return true
    else return false;
  }

}
