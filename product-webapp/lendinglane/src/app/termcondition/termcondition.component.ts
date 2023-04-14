import { Component, OnInit } from '@angular/core';
import { LoanService } from '../service/loan.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-termcondition',
  templateUrl: './termcondition.component.html',
  styleUrls: ['./termcondition.component.css']
})
export class TermconditionComponent implements OnInit {

  constructor(private loanservice:LoanService,private route:Router) { }

  ngOnInit(): void {
  }

accept()
{
this.loanservice.valid=true;
this.route.navigate(['/dashboard/loan'])

}

decline()
{
this.loanservice.valid=false;
this.route.navigate(['/dashboard/loan'])
}




}
