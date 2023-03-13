import { BorrowerDetailsService } from './../../service/borrower-details.service';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';


@Component({
  selector: 'app-borrower-details',
  templateUrl: './borrower-details.component.html',
  styleUrls: ['./borrower-details.component.css']
})
export class BorrowerDetailsComponent implements OnInit {

  borrowerDetails: any;
  editMode=false;

  showPassword: boolean = false;
  showConfirmPassword: boolean = false;

  constructor(private borrowerService: BorrowerDetailsService, private route: ActivatedRoute,private http:HttpClient) { }

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
    // Update the borrower details in the database using a PUT request
    const emailId = localStorage.getItem('email') ?? '';
    this.http.put(`http://localhost:8083/api/v1/borrower/borrowers/${emailId}`, borrower)
      .subscribe(
        response => {
          this.editMode = false;
          this.getBorrowerDetails(emailId);
        },
        error => {
          console.log(error);
        }
      );
  }
}
