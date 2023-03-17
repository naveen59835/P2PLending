import { BorrowerDetailsService } from './../../service/borrower-details.service';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Borrower } from 'src/app/model/Borrower';

@Component({
  selector: 'app-borrower-details',
  templateUrl: './borrower-details.component.html',
  styleUrls: ['./borrower-details.component.css'],
})
export class BorrowerDetailsComponent implements OnInit {
  borrowerDetails: Borrower = {
    address: {
      address: '',
      city: '',
      pin: '',
      state: '',
    },
  };
  editMode = false;
  showPassword = false;
  showConfirmPassword = false;
  formGroup: any;
  aadharImage: any;

  constructor(
    private borrowerService: BorrowerDetailsService,
    private route: ActivatedRoute,
    private http: HttpClient,
    private fb: FormBuilder
  ) {
    this.formGroup = this.fb.group({
      firstName: ['', [Validators.minLength(3)]],
      lastName: ['', [Validators.minLength(3)]],
      aadhaarNo: ['', [Validators.minLength(12)]],
      panNo: ['', [Validators.minLength(10)]],
      phoneNo: ['', [Validators.minLength(10)]],
      address: this.fb.group({
        address: ['', [Validators.minLength(3)]],
        city: ['', [Validators.minLength(3)]],
        state: ['', [Validators.minLength(3)]],
        pin: ['', [Validators.minLength(3)]],
      }),
    });
  }

  ngOnInit(): void {
    const emailId = localStorage.getItem('email') ?? '';
    this.getBorrowerDetails(emailId);
  }

  getBorrowerDetails(emailId: string) {
    this.borrowerService.getBorrowerDetails(emailId).subscribe(
      (response) => {
        this.borrowerDetails = response;
      },
      (error) => {
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
    formData.append(
      'details',
      new Blob([JSON.stringify(this.borrowerDetails)], {
        type: 'application/json',
      })
    );
   // formData.append('aadhar', this.aadharImage);
    this.http
      .put<Borrower>(
        `http://localhost:8083/api/v1/borrower/borrowers/${emailId}`,
        formData
      )
      .subscribe(
        (response) => {
          this.editMode = false;
        },
        (error) => {
          console.log(error);
        }
      );
  }

  // onCreate(borrower: any) {
  //   const emailId = localStorage.getItem('email') ?? '';
  //   this.http
  //     .post<Borrower>(
  //       `http://localhost:8083/api/v1/borrower/register/`,
  //       this.borrowerDetails
  //     )
  //     .subscribe(
  //       (response) => {
  //         this.editMode = false;
  //         console.log('Borrower created successfully!');
  //       },
  //       (error) => {
  //         console.log(error);
  //       }
  //     );
  // }

  isFieldInvalid(field: string) {
    return (
      !this.formGroup.get(field)?.valid && this.formGroup.get(field)?.touched
    );
  }
  aadharImageChange($event: any) {
    let data = new FormData();
    this.aadharImage = $event.target.files[0];
    data.append("image",$event.target.files[0])

    this.http.put("http://localhost:8083/api/v1/borrower/borrowers/image/"+localStorage.getItem("email"),data).subscribe(
      {
        next : (data)=> console.log(data)

      }
    )
  }

}
