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
  disablede1=true;
 istrue=false;
 value:any;


  lender:any={
    address: {
      address: undefined,
      city: undefined,
      pin: undefined,
      state: undefined
    }
  }
 
  


    constructor(private lenderService:LenderService,private _snackBar:MatSnackBar,private route:Router) { 
       this.id=window.localStorage.getItem("email");
     
     
      
    
    }
   
    ngOnInit(): void {
      this.lenderService.getLenderById(this.id).subscribe((data:any)=>{
  
        this.lender=data
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
  toggleDisabledd()
{
  this.disablede1 = !this.disablede1;
}


  lenderupdateaddress(lenderaddressForm:NgForm)
  {
  

  if(lenderaddressForm.valid)
  {
  this.lenderService.updateLender(this.lender,this.lender.emailId).subscribe(data=>{
    this.lender=data
   
   
    this._snackBar.open("successfully updated the detail", "success", 
    {
      duration: 5000,
      panelClass: ['mat-toolbar', 'mat-primary']
    })
    
  });
  this.disablede=true;
}

else{
  this._snackBar.open("error-occur", "failure", 
  {
    duration: 5000,
    panelClass: ['mat-toolbar', 'mat-primary']
  })
}


  }

valuee:any
  aadharImageChange($event:any){
    let data = new FormData();

    
    data.append("aadhaar",$event.target.files[0])
    this.lenderService.updateAadharImage(this.id,data).subscribe(data=>{
      console.log(data.length)
     
      this._snackBar.open("successfully updated the detail", "success", 
      {
        
        duration: 5000,
        panelClass: ['mat-toolbar', 'mat-primary']
      })
      this.valuee="true"
    })
    this.valuee="false"
    this.disabled=true
  }
   
  panImageChange($event:any)
  {
   
   
    let data = new FormData();
    ;
   
   
   
    data.append("pan",$event.target.files[0])
    this.lenderService.updatePanImage(this.id,data).subscribe({
      next:data=>{
      console.log(data)
      
      this._snackBar.open("successfully updated the detail", "success", 
      {
        
        duration: 5000,
        panelClass: ['mat-toolbar', 'mat-primary']
      })
        this.value="true"
      
     
      },
  
  
    })
    this.value="false"
    this.disabled=true
  }

  lenderupdateamountDetail(lenderamountForm:NgForm)
  {
    this.lenderService.updateLender(this.lender,this.lender.emailId).subscribe(data=>{
      this.lender=data
     
    
      this._snackBar.open("successfully updated the detail", "success", 
      {
        
        duration: 5000,
        panelClass: ['mat-toolbar', 'mat-primary']
      })


     

      
    });

    this.disablede1=true;
  }





btn()
{
  this.istrue=!this.istrue
}

}
