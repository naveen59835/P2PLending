import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SidenavService {

  isSidenavOpened = true;
  isSmallScreen = false;
  constructor() { }
}
