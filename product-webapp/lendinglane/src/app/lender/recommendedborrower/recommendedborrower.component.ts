import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { DomSanitizer } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';
import { BorrowerDetailsService } from 'src/app/service/borrower-details.service';
import { ReviewService } from 'src/app/service/review.service';

@Component({
  selector: 'app-recommendedborrower',
  templateUrl: './recommendedborrower.component.html',
  styleUrls: ['./recommendedborrower.component.css']
})
export class RecommendedborrowerComponent implements OnInit {
  id:any;
  borrower:any={
    address: {
      address: '',
      city: '',
      pin: '',
      state: '',
    },
  }
  aadharImage:any;
  panImage:any;
  istrue=false;
  rating=false
  iscard=true;
  lenderid:any;
  Lenderid:any
  borrowerrating:any=0

  Rating: number=0;
  public form: FormGroup;
  Review:any;
    constructor(private activateRoute:ActivatedRoute,private service:BorrowerDetailsService,private sanitizer:DomSanitizer,private fb: FormBuilder,private _snackBar: MatSnackBar,private reviewservice:ReviewService) {
      this.activateRoute.paramMap.subscribe(data=>{
        this.id=data.get('id')??0;
      });

      this.lenderid=localStorage.getItem('email')
      this.Lenderid=localStorage.getItem('email')
      this.form = this.fb.group({

        review: [''],
        stars:[''],
        lenderId:this.lenderid

      });


    }

    ngOnInit(): void {

      this.activateRoute.paramMap.subscribe(data=>{
         this.id=data.get('id')??0;

         this.reviewservice.getrating(this.id).subscribe({
           next : (data)=>{this.borrowerrating=data},
           error : err => console.log(err)
         })

        this.service.getBorrowerDetails(this.id).subscribe(data=>{
          this.borrower=data;
          let object='data:image/png;base64,'+data.aadharImage
         this.aadharImage= this.sanitizer.bypassSecurityTrustUrl(object)

          object='data:image/png;base64,'+data.panImage
         this.panImage =this.sanitizer.bypassSecurityTrustUrl(object)

        })
      })



    }




    onclick()
    {
      this.istrue=!this.istrue
    }

  btn()
  {
  this.rating=true
  this.iscard=false
  }

  card()
  {

    this.iscard=true
    this.rating=false
  }

  review()
  {

    this.reviewservice.addreview(this.id,this.form.value).subscribe({

   next:data=>{
    console.log(data)
    this._snackBar.open("successfully submitted", "success",
        {

          duration: 5000,
          panelClass: ['mat-toolbar', 'mat-primary']
        });
        this.form.reset()
        this.form.get('lenderId')?.setValue(this.lenderid)
   },
      error:error=>{
        console.log(error )
        this._snackBar.open("you have already given the review", "success",
        {

          duration: 5000,
          panelClass: ['mat-toolbar', 'mat-primary']
        });

        this.form.reset()
        this.form.get('lenderId')?.setValue(this.lenderid)
      },





    })


    this.getrating(this.id)


  }

  getrating(id:any)
  {
    this.reviewservice.getrating(this.id).subscribe(data=>{
      this.borrowerrating=data
    })
  }



}
