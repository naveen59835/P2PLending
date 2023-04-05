import { HttpClient } from '@angular/common/http';
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
 aadhaarNumbers: string[] = [];
 aadhaarVerificationMessage = '';
 panNumbers: string[] = [];
 panVerificationMessage = '';




  lender:any={
    address: {
      address: undefined,
      city: undefined,
      pin: undefined,
      state: undefined
    }
  }




    constructor(private lenderService:LenderService,private _snackBar:MatSnackBar,private route:Router,private http:HttpClient) {
       this.id=window.localStorage.getItem("email");




    }

    ngOnInit(): void {
      this.lenderService.getLenderById(this.id).subscribe((data:any)=>{

        this.lender=data
      })
      this.http.get<any>('http://localhost:3000/AadhaarNumbers').subscribe(data => {
        this.aadhaarNumbers = data;
      });
      this.http.get<any>('http://localhost:3001/panNumbers').subscribe(data => {
        this.panNumbers = data;
      });

    }

    verifyAadhaar() {
      if (this.lender.aadhaar && this.lender.aadhaar.length >= 12) {
        if (this.aadhaarNumbers.includes(this.lender.aadhaar)) {

          this.aadhaarVerificationMessage = 'Aadhaar verified.';
          return 'green';
        } else {

          this.aadhaarVerificationMessage = 'Enter a Valid Aadhaar.';
          return 'red';
        }
      } else if (this.lender.aadhaar && this.lender.aadhaar.length < 12) {
        this.aadhaarVerificationMessage = 'Aadhaar number must be at least 12 characters long.';
      } else {
        this.aadhaarVerificationMessage = '';
      }
      return '';
    }

    isAadhaarVerified() {
      return this.aadhaarVerificationMessage === 'Aadhaar verified.';
    }

    verifyPan() {
      if (this.lender.pan && this.lender.pan.length >= 10) {
        if (/^([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}?$/.test(this.lender.pan)) {
          this.panVerificationMessage = 'PAN verified.';
          return 'green';
        } else {
          this.panVerificationMessage = 'Enter a Valid PAN.';
          return 'red';
        }
      } else if (this.lender.pan && this.lender.pan.length < 10) {
        this.panVerificationMessage = 'PAN Number must be at least 10 characters long.';
      } else {
        this.panVerificationMessage = '';
      }
      return '';
    }


    isPanVerified() {
      return this.panVerificationMessage === 'PAN verified.';
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





    data.append("pan",$event.target.files[0])
    this.lenderService.updatePanImage(this.id,data).subscribe({
      next:data=>{




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
      this.route.navigate(['/dashboard'])




    });

    this.disablede1=true;
  }





btn()
{
  this.istrue=!this.istrue
}

}
