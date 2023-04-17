import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, ActivatedRouteSnapshot, Router} from "@angular/router";
import {SidenavService} from "../service/sidenav.service";

@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.css']
})
export class SidenavComponent implements OnInit {
  selected = "home";
  constructor(private router : Router, private sidenavService : SidenavService) { }

  ngOnInit(): void {
    let snap = this.router.url.split("/dashboard/");
    if(snap.length>1){
      this.selected = snap[1]
    }
  }
  changeSelcted(selectValue:any,route:any){
    console.log(this.sidenavService.isSmallScreen)
    if(this.sidenavService.isSmallScreen){
      this.sidenavService.isSidenavOpened=true
    }
    this.selected = selectValue;
    this.router.navigateByUrl(route)
  }
  role = localStorage.getItem("role") || ""
}
