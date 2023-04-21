import {Component, OnInit, ViewChild} from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {LoanService} from "../../service/loan.service";
import {SidenavService} from "../../service/sidenav.service";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {LoanDialogComponent} from "../../borrower/borrower-loans/loan-dialog/loan-dialog.component";

@Component({
  selector: 'app-lender-loans',
  templateUrl: './lender-loans.component.html',
  styleUrls: ['./lender-loans.component.css']
})
export class LenderLoansComponent implements OnInit {

  constructor(private dialog: MatDialog, private  loanService : LoanService,private sidenav : SidenavService) {
  }

  data : any = []
  loanData :MatTableDataSource<any> = new MatTableDataSource<any>([]);
  loanStatus = "ongoing";
  displayedColumns =["#","amount","terms","closed","expand"]
  ngOnInit(): void {
    this.loanService.getAllLoans(localStorage.getItem('email') || '',localStorage.getItem('role')|| '').subscribe({
      next : (data=>{
        this.data = data;
        this.changeLoanStatus(this.loanStatus)
      }),
      error : (err)=> console.log(err)
    })

  }
  @ViewChild("paginator") paginator !: MatPaginator;
  openLoanDialog() {
    let dialog = this.dialog.open(LoanDialogComponent, {
      maxWidth: '500px',
      width:'100%'
    })
  }
  changeLoanStatus(value:string){
    this.loanStatus = value;
    if(this.data.length>0){
      if(value === "ongoing") {
        this.loanData = new MatTableDataSource<any>(this.data.filter((loan: any) =>!loan.expired&&loan.approved))
      }else if(value==="finished"){
        this.loanData = new MatTableDataSource<any>(this.data.filter((loan: any) =>loan.expired))
      }
    }
    this.loanData.paginator = this.paginator;

  }
  get isSmallScreen(){
    return this.sidenav.isSmallScreen;
  }

}
