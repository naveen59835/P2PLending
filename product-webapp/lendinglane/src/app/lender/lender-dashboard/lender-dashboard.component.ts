import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RecommendedBorrower } from 'src/app/model/RecommendedBorroer';
import { LenderService } from 'src/app/service/lender.service';
import { RecommendationService } from 'src/app/service/recommendation.service';

@Component({
  selector: 'app-lender-dashboard',
  templateUrl: './lender-dashboard.component.html',
  styleUrls: ['./lender-dashboard.component.css']
})
export class LenderDashboardComponent implements OnInit {

  borrower:RecommendedBorrower[]=[];
  loans:any=[{}];
  
  istrue=true
  
  
  
  lender:any={
    address: {
      address: undefined,
      city: undefined,
      pin: undefined,
      state: undefined
    }
  }
  
  constructor(private service:RecommendationService,private lenderservice:LenderService,private route:Router) { 
  
  
  }
    ngOnInit(): void {
   
  
      this.lenderservice.getLenderById(localStorage.getItem('email')).subscribe(data=>{
  
        this.lender=data;
        if(this.lender.creditScore==null)
        {
  
          this.add();
        }
          
    
     
    
  
  
      // this.service.LenderCreditScore
      console.log(this.lender.creditScore)
      this.service.getbBorrower(this.lender.creditScore).subscribe(data=>{
        this.borrower=data;
         
        
       });
    
      });
  
  
    }
  
  
  
  
  
  
    creditScore()
    {
      
     
        this.route.navigate(['/dashboard/profile'])
        this.istrue=true
  
      
  
    }
  
  add()
  {
  
  
  this.istrue=!this.istrue
  
  
  }
  
  

}
