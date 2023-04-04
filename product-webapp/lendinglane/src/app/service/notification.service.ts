import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  constructor(private  http : HttpClient) { }

  getAllNotifications(){
    return this.http.get("http://localhost:9002/api/v1/notification/getAll/"+localStorage.getItem("email"))
  }
}
