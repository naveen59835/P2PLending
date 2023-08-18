import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class NavbarToggleService {
  toggleOpened = true;
  constructor() { }
  toggleSidenav(){
    console.log(this.toggleOpened)
    this.toggleOpened=!this.toggleOpened;
  }
}
