import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {
  private URL !: string

  constructor(private httpClient:HttpClient) { }

  registerUser(data:any){
    this.URL = `http://localhost:9002/api/v1`; //change this with api gateway asap,
    return this.httpClient.post(this.URL+`/${data.role}/register`,data);
  }

}
