import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-lender',
  templateUrl: './lender.component.html',
  styleUrls: ['./lender.component.css']
})
export class LenderComponent implements OnInit {
  showFiller = false;
  constructor(private route:Router) { }



  ngOnInit(): void {
  }







}
