import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {
  private URL = "http://localhost:8082/api/v1"; //change this with api gateway asap,

  constructor(private httpClient:HttpClient) { }

  registerUser(data:any){
    return this.httpClient.post(this.URL+`/${data.role}/register`,data);
  }
}
