import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.css']
})
export class SidenavComponent implements OnInit {
  selected = "home";
  constructor(private router : Router) { }

  ngOnInit(): void {
  }
  changeSelcted(selectValue:any,route:any){
    this.selected = selectValue;
    this.router.navigateByUrl(route)
  }
}
