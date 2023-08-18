import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {MatDialog, MatDialogRef} from "@angular/material/dialog";
import {LoanDialogComponent} from "./loan-dialog/loan-dialog.component";
import {LoanService} from "../../service/loan.service";
import {MatPaginator} from "@angular/material/paginator";
import {MatTableDataSource} from "@angular/material/table";
import {SidenavService} from "../../service/sidenav.service";

@Component({
  selector: 'app-borrower-loans',
  templateUrl: './borrower-loans.component.html',
  styleUrls: ['./borrower-loans.component.css'],
})
export class BorrowerLoansComponent implements OnInit{

  constructor(private dialog: MatDialog, private  loanService : LoanService,private sidenav : SidenavService) {
  }

  data : any = []
  loanData :MatTableDataSource<any> = new MatTableDataSource<any>([]);
  loanStatus = "ongoing";
  displayedColumns =["#","amount","terms","expired","expand"]
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
      }else{
        this.loanData = new MatTableDataSource<any>(this.data.filter((loan: any) =>!loan.approved))
      }
    }
    this.loanData.paginator = this.paginator;

  }
  get isSmallScreen(){
    return this.sidenav.isSmallScreen;
  }
}
