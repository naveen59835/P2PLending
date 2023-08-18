import {HttpClient} from '@angular/common/http';
import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {MatSnackBar} from '@angular/material/snack-bar';
import {Router} from '@angular/router';
import {LenderService} from 'src/app/service/lender.service';

@Component({
  selector: 'app-lender-details',
  templateUrl: './lender-details.component.html',
  styleUrls: ['./lender-details.component.css']
})
export class LenderDetailsComponent implements OnInit {

  lenderDetails: any = {
    creditScore:"600-700",
    address: {
      address: undefined,
      city: undefined,
      pin: undefined,
      state: undefined
    }
  }
  id: any;
  editMode = false;
  formGroup: any;
  aadharImage: any;
  cibilImage: any;
  panImage: any;
  aadhaarNumbers: string[] = [];
  aadhaarVerificationMessage = '';
  panNumbers: string[] = [];
  panVerificationMessage = '';
  toggleForm = "personal"
  editImage!: string
  uploadButton!: any;

  constructor(private lenderService: LenderService, private _snackBar: MatSnackBar, private route: Router, private http: HttpClient, private fb: FormBuilder) {
    this.id = window.localStorage.getItem("email");
    this.formGroup = this.fb.group({
      firstName: ['', [Validators.minLength(3)]],
      lastName: ['', [Validators.minLength(3)]],
      aadhaar: ['', [Validators.minLength(12)]],
      pan: ['', [Validators.minLength(10)]],
      phoneNo: ['', [Validators.minLength(10)]],
      address: this.fb.group({
        address: ['', [Validators.minLength(3)]],
        city: ['', [Validators.minLength(3)]],
        state: ['', [Validators.minLength(3)]],
        pin: ['', [Validators.minLength(3)]],
      }),
      amountToInvest: ["", Validators.required],
      creditScore: ['', Validators.required]
    });
  }

  ngOnInit() {
    this.lenderService.getLenderById(this.id).subscribe((data: any) => {
      this.lenderDetails = data
    })
  }

  verifyAadhaar() {
    if (this.lenderDetails.aadhaar && this.lenderDetails.aadhaar.length >= 12) {
      if (this.aadhaarNumbers.includes(this.lenderDetails.aadhaar)) {

        this.aadhaarVerificationMessage = 'Aadhaar verified.';
        return 'green';
      } else {

        this.aadhaarVerificationMessage = 'Enter a Valid Aadhaar.';
        return 'red';
      }
    } else if (this.lenderDetails.aadhaar && this.lenderDetails.aadhaar.length < 12) {
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
    if (this.lenderDetails.pan && this.lenderDetails.pan.length >= 10) {
      if (/^([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}?$/.test(this.lenderDetails.pan)) {
        this.panVerificationMessage = 'PAN verified.';
        return 'green';
      } else {
        this.panVerificationMessage = 'Enter a Valid PAN.';
        return 'red';
      }
    } else if (this.lenderDetails.pan && this.lenderDetails.pan.length < 10) {
      this.panVerificationMessage = 'PAN Number must be at least 10 characters long.';
    } else {
      this.panVerificationMessage = '';
    }
    return '';
  }

  isPanVerified() {
    return this.panVerificationMessage === 'PAN verified.';
  }

  getLenderDetails(emailId: string) {
    this.lenderService.getLenderById(emailId).subscribe(
      (response: any) => {
        console.log(response)
        this.aadharImage = response.aadharImage;
        this.panImage = response.panImage;
        this.cibilImage = response.cibilImage;
        this.lenderDetails = response;
      },
      (error) => {
        console.log(error);
      }
    );
  }

  onEdit() {
    this.editMode = true;
  }

  onSave(lender: any) {
    const emailId = localStorage.getItem('email') ?? '';
    console.log(this.lenderDetails)
    this.lenderService.updateLender(this.lenderDetails, emailId).subscribe({
        next: (response) => {
          this.editMode = false;
        },
        error: err => console.log(err)
      }
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
    if (name === "aadhar") {
      this.lenderService.updateAadharImage(localStorage.getItem("email"), formData).subscribe({
        next: (data) => {
          this._snackBar.open("successfully updated the detail", "success", {
            duration: 5000,
            panelClass: ['mat-toolbar', 'mat-primary']
          })
        }
      })
    } else if (name === "pan") {
      this.lenderService.updatePanImage(localStorage.getItem("email"), formData).subscribe({
        next: (data) => {
          this._snackBar.open("successfully updated the detail", "success", {
            duration: 5000,
            panelClass: ['mat-toolbar', 'mat-primary']
          })
        }
      })
    }
  }

  isFieldInvalid(field: string) {
    return (
      !this.formGroup.get(field)?.valid && this.formGroup.get(field)?.touched
    );
  }
  onUploadButtonClick(event: any, name: string) {
    const fileInput = document.getElementById(`${name}`) as HTMLInputElement;
    if (fileInput.files && fileInput.files.length > 0) {
      const file = fileInput.files[0];
      const fileNameElement = document.getElementById(`${name}ImageLabel`);
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
