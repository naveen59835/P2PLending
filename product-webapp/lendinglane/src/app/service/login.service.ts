import { Router } from '@angular/router';
import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  baseUrl = "http://localhost:9002/api/v1/authentication";
  constructor(private http : HttpClient,private router:Router) { }
  public login(loginData:any){
    return this.http.post("http://localhost:9002/api/v1/authentication/login",loginData)
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
  getTotalUsers(){
    return this.http.get(`${this.baseUrl}/totalUsers`);
  }
}
