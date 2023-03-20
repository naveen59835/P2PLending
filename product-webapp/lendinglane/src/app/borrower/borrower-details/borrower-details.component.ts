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
  borrowerDetails: any = {
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

extractFileAndUpload(file: File, name: string) {
  const maxSize = 10 * 1024 * 1024; // 10 MB in bytes
  if (file.size > maxSize) {
    const messageElement = document.getElementById(`${name}UploadMessage`);
    if (messageElement) {
      messageElement.innerHTML = 'File size exceeds the maximum limit of 10 MB.';
    }
    return;
  }

  const formData = new FormData();
  formData.append(name, file);

  this.http.put("http://localhost:8083/api/v1/borrower/borrowers/image/" + localStorage.getItem("email"), formData)
    .subscribe({
      next: (data) => {
        console.log(data);
        const messageElement = document.getElementById(`${name}UploadMessage`);
        if (messageElement) {
          messageElement.innerHTML = 'Image uploaded successfully.';
        }
      },
      error: (error) => {
        console.log(error);
        const messageElement = document.getElementById(`${name}UploadMessage`);
        if (messageElement) {
          messageElement.innerHTML = 'File size exceeds the maximum limit of 10 MB.';
        }
      }
    });
}

onUploadButtonClick(event: any, name: string) {
  const fileInput = document.getElementById(`${name}ImageInput`) as HTMLInputElement;
  if (fileInput.files && fileInput.files.length > 0) {
    const file = fileInput.files[0];
    this.extractFileAndUpload(file, name);
      this.editMode = false;

  }

}

}
