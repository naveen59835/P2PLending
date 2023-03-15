import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Lender } from 'src/app/model/lender';
import { LenderService } from 'src/app/service/lender.service';

@Component({
  selector: 'app-lender-details',
  templateUrl: './lender-details.component.html',
  styleUrls: ['./lender-details.component.css']
})
export class LenderDetailsComponent implements OnInit {
  id:any="";
  disabled = true;
  disablede = true;
  lenderaddress:any={
    address: {
      address: undefined,
      city: undefined,
      pin: undefined,
      state: undefined
    }
  };
  lender:any
 
  


    constructor(private lenderService:LenderService,private _snackBar:MatSnackBar,private route:Router) { 
       this.id=window.localStorage.getItem("email");
     
     
   
    
    }
   
    ngOnInit(): void {
      this.lenderService.getLenderById(this.id).subscribe(data=>{
  
        this.lender=data
      })
      this.lenderService.getLenderById(this.id).subscribe(data=>{
  
        this.lenderaddress=data 
      })

    }
  
  lenderupdate(lenderfrom:NgForm)
  {
  
   
   if(lenderfrom.valid)
   {
    this.lenderService.updateLender(this.lender,this.lender.emailId).subscribe(
      {
     
     next:data=>
     {

     
      this.lender=data
      
    this._snackBar.open("successfully updated the detail", "success", 
    {
      duration: 5000,
      panelClass: ['mat-toolbar', 'mat-primary']
    })
      this.disabled=true;
  }


});}
else{
    
this._snackBar.open("error occur", "failure", {
  duration: 5000,
  panelClass: ['mat-toolbar', 'accent']
})
  this.disabled=true
}
 
  }

  

   toggleDisabled(): void {
    this.disabled = !this.disabled;
  }

  toggleDisabled1(): void {
    this.disablede = !this.disablede;
  }


  lenderupdateaddress(lenderaddressForm:NgForm)
  {
  this.route.navigate(['/dashboard/lenderaddress'])
  }



}
