import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  constructor(private  http : HttpClient) { }

//apiBaseUrl = environment.apiBaseUrl + '/notification-service';
  getAllNotifications(){
  //return this.http.get( this.apiBaseUrl + "/api/v1/notification/getAll/"+localStorage.getItem("email"))
   return this.http.get("https://lendinglane.stackroute.io/api/v1/notification/getAll/"+localStorage.getItem("email"))
  }
}
