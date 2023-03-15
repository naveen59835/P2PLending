import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Address } from 'src/app/model/lender';
import { Lender } from 'src/app/model/lender';
import { LenderService } from 'src/app/service/lender.service';

@Component({
  selector: 'app-lender-address-detail',
  templateUrl: './lender-address-detail.component.html',
  styleUrls: ['./lender-address-detail.component.css']
})
export class LenderAddressDetailComponent implements OnInit {
  id:any="";
  lender:any;
  address:Address={}
    constructor(private lenderService:LenderService,private route:Router,private snackbar:MatSnackBar) { 
       this.id=window.localStorage.getItem("email");
      
    }
   
    ngOnInit(): void {
      this.lenderService.getLenderById(this.id).subscribe(data=>{
  
        this.lender=data
      })
    }
  
  lenderupdateaddress(lenderfrom:NgForm)
  {
    
    this.lenderService.updateLenderAddress(this.address,this.lender.emailId).subscribe(data=>{
      this.lender=data
     
      this.route.navigate(['/dashboard/profile'])
      this.snackbar.open("successfully updated the detail", "success", 
      {
        duration: 5000,
        panelClass: ['mat-toolbar', 'mat-primary']
      })
      
    })
  }
  

}
