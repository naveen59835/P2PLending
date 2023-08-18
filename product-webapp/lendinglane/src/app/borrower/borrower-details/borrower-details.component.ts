import { BorrowerDetailsService } from './../../service/borrower-details.service';
import { HttpClient } from '@angular/common/http';
import { Component, ElementRef, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Borrower } from 'src/app/model/Borrower';
import { ViewChild } from '@angular/core';
import {SidenavService} from "../../service/sidenav.service";

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
  cibilImage: any;
  panImage: any;
  aadhaarNumbers: string[] = [];
  aadhaarVerificationMessage = '';
  panNumbers: string[] = [];
  panVerificationMessage = '';
  toggleForm = "personal"
  editImage!:string
  uploadButton!:any;

  constructor(
    private borrowerService: BorrowerDetailsService,
    private route: ActivatedRoute,
    private http: HttpClient,
    private fb: FormBuilder,
    private sidenav : SidenavService
  ) {
    this.formGroup = this.fb.group({
      firstName: ['', [Validators.minLength(3)]],
      lastName: ['', [Validators.minLength(3)]],
      aadhaarNo: ['', [Validators.minLength(12)]],
      panNo: ['', [Validators.minLength(10)]],
      phoneNo: ['', [Validators.minLength(10)]],
      cibilScore: ['', [Validators.maxLength(3)]],
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
    this.http.get<any>('http://localhost:3000/AadhaarNumbers').subscribe(data => {
      this.aadhaarNumbers = data;
    });
    this.http.get<any>('http://localhost:3001/panNumbers').subscribe(data => {
      this.panNumbers = data;
    });

  }

  get isSmallScreen(){
    return this.sidenav.isSmallScreen;
  }
  verifyAadhaar() {
    if (this.borrowerDetails.aadhaarNo && this.borrowerDetails.aadhaarNo.length >= 12) {
      if (this.aadhaarNumbers.includes(this.borrowerDetails.aadhaarNo)) {
        // Aadhaar verified
        this.aadhaarVerificationMessage = 'Aadhaar verified.';
        return 'green';
      } else {
        // Aadhaar not verified
        this.aadhaarVerificationMessage = 'Enter a Valid Aadhaar.';
        return 'red';
      }
    } else if (this.borrowerDetails.aadhaarNo && this.borrowerDetails.aadhaarNo.length < 12) {
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
    if (this.borrowerDetails.panNo && this.borrowerDetails.panNo.length >= 10) {
      if (this.panNumbers.includes(this.borrowerDetails.panNo)) {
        this.panVerificationMessage = 'PAN verified.';
        return 'green';
      } else {
        this.panVerificationMessage = 'Enter a Valid PAN.';
        return 'red';
      }
    } else if (this.borrowerDetails.panNo && this.borrowerDetails.panNo.length < 10) {
      this.panVerificationMessage = 'PAN Number must be at least 12 characters long.';
    } else {
      this.panVerificationMessage = '';
    }
    return '';
  }

  isPanVerified() {
    return this.panVerificationMessage === 'PAN verified.';
  }


  getBorrowerDetails(emailId: string) {
    this.borrowerService.getBorrowerDetails(emailId).subscribe(
      (response) => {
        console.log(response)
        this.aadharImage=response.aadharImage;
        this.panImage=response.panImage;
        this.cibilImage=response.cibilImage;
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
    this.http
      .put<Borrower>(
        `http://localhost:9002/api/v1/borrower/borrowers/${emailId}`,
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
    const messageElement = document.getElementById(`${name}`);
    if (messageElement) {
      messageElement.innerHTML = 'File size exceeds the maximum limit of 10 MB.';
    }
    return;
  }

  const formData = new FormData();
  formData.append(name, file);

  this.http.put("http://localhost:9002/api/v1/borrower/borrowers/image/" + localStorage.getItem("email"), formData)
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
  const fileInput = document.getElementById(`${name}`) as HTMLInputElement;
  if (fileInput.files && fileInput.files.length > 0) {
    const file = fileInput.files[0];
    const fileNameElement = document.getElementById(`${name}ImageLabel`);
    console.log(fileNameElement)
    if (fileNameElement) {
      fileNameElement.innerText = file.name;
    }
    this.extractFileAndUpload(file, name);
    this.uploadButton=undefined;
    this.editImage='';
    this.editMode = false;
  }
}

}
