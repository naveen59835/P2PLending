import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {
  private URL !: string

  constructor(private httpClient:HttpClient) { }

  registerUser(data:any){
    this.URL = `http://localhost:${data.role==="borrower"?8083:8086}/api/v1`; //change this with api gateway asap,
    return this.httpClient.post(this.URL+`/${data.role}/register`,data);
  }

}
