import { Router } from '@angular/router';
import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class LoginService {

// apiBaseUrl = environment.apiBaseUrl + '/login-service';
  baseurl = 'https://lendinglane.stackroute.io/api/v1/authentication'

  constructor(private http : HttpClient,private router:Router) { }
  public login(loginData:any){
   //  return this.http.post(this.apiBaseUrl+ "/api/v1/authentication/login",loginData)
    return this.http.post(this.baseurl + "/login",loginData)
  }

  public isAuthenticated(): boolean {
    const token = window.localStorage.getItem('token');
    return !!token;
  }

  public logout() {
    // Clear local storage
    localStorage.removeItem('userName');
    localStorage.removeItem('email');
    localStorage.removeItem('token');
    localStorage.removeItem('role');

    // Navigate to login page
    this.router.navigate(['/login']);
  }
}
