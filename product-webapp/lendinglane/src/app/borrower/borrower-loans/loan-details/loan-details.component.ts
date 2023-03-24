import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {LoanService} from "../../../service/loan.service";

@Component({
  selector: 'app-loan-details',
  templateUrl: './loan-details.component.html',
  styleUrls: ['./loan-details.component.css']
})
export class LoanDetailsComponent implements OnInit {

  constructor(private activatedRoute : ActivatedRoute, private loanService : LoanService, private router : Router) { }
  loanData!:any;
  ngOnInit(): void {
    this.activatedRoute.params.subscribe({
      next :( data:any)=>this.getLoan(data?.id)
    })
  }
  getLoan(loanId : string){
    this.loanService.getLoan(loanId).subscribe({
      next : (data)=> {
        this.loanData = data
        console.log(data)
      },
      error : (err)=> this.router.navigateByUrl("not-found")
    })
  }

}
