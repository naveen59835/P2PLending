import { BorrowerDetailsService } from './../../service/borrower-details.service';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FormGroup } from '@angular/forms';
import {Borrower} from "../../model/Borrower";


@Component({
  selector: 'app-borrower-details',
  templateUrl: './borrower-details.component.html',
  styleUrls: ['./borrower-details.component.css']
})
export class BorrowerDetailsComponent implements OnInit {

  borrowerDetails: any = {
    address:{
      address: "",
      city: "",
      pin: "",
      state: "",
    }
  };

  editMode=false;

  showPassword: boolean = false;
  showConfirmPassword: boolean = false;
  formGroup: any;
  aadharImage:any;

  constructor(private borrowerService: BorrowerDetailsService, private route: ActivatedRoute,private http:HttpClient) {
  }

  ngOnInit(): void {
    const emailId = localStorage.getItem('email') ?? '';
    this.getBorrowerDetails(emailId);

  }

  getBorrowerDetails(emailId: string) {
    this.borrowerService.getBorrowerDetails(emailId)
      .subscribe(
        response => {
          this.borrowerDetails = response;
        },
        error => {
          console.log(error);
        }
      );
  }
  onEdit() {
    this.editMode = true;

  }

  onSave(borrower: any) {
    const emailId = localStorage.getItem('email') ?? '';
    let formData = new FormData();
    formData.append("details",new Blob([JSON.stringify(this.borrowerDetails)],{
      type:"application/json"
    }));
    formData.append("aadhar",this.aadharImage)
    this.http.put<Borrower>(`http://localhost:8083/api/v1/borrower/borrowers/${emailId}`,this.borrowerDetails)
    .subscribe(
      response => {
        this.editMode = false;
      },
      error => {
        console.log(error);
      }
    );
  }

  onCreate(borrower: any) {
    const emailId = localStorage.getItem('email') ?? '';
    this.http.post<Borrower>(`http://localhost:8083/api/v1/borrower/register/${emailId}`, this.borrowerDetails)
    .subscribe(
      response => {
        this.editMode = false;
        console.log('Borrower created successfully!');
      },
      error => {
        console.log(error);
      }
    );
  }

  isFieldInvalid(field: string) {
    return !this.formGroup.get(field)?.valid && this.formGroup.get(field)?.touched;
  }
  aadharImageChange($event:any){
    // let data = new FormData();
    this.aadharImage=$event.target.files[0];
    // data.append("aadhar",$event.target.files[0])
    // this.http.put("http://localhost:8083/api/v1/borrower/borrowers/image/"+localStorage.getItem("email"),data).subscribe(
    //   {
    //     next : (data)=> console.log(data)
    //   }
    // )
  }

}


