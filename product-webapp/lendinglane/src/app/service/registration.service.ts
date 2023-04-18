import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {
  private URL !: string

 //apiBaseUrl = environment.apiBaseUrl + "/registration-service";

  constructor(private httpClient:HttpClient) { }

  registerUser(data:any){
    this.URL = `https://lendinglane.stackroute.io/api/v1`; //change this with api gateway asap,
    return this.httpClient.post(this.URL+`/${data.role}/register`,data);
  }

}
