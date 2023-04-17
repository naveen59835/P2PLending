import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {RecommendedBorrower} from 'src/app/model/RecommendedBorroer';
import {LenderService} from 'src/app/service/lender.service';
import {RecommendationService} from 'src/app/service/recommendation.service';
import {LoanService} from "../../service/loan.service";
import {PaymentService} from "../../service/payment.service";
import Swal from "sweetalert2";
import {ChatService} from "../../service/chat.service";
declare let Razorpay: any;

@Component({
  selector: 'app-lender-dashboard',
  templateUrl: './lender-dashboard.component.html',
  styleUrls: ['./lender-dashboard.component.css']
})
export class LenderDashboardComponent implements OnInit {

  borrower: RecommendedBorrower[] = [];
  loans: any = [{}];
  displayedColumns = ["Borrowers","Amount","Rating", "Payment", "Chat", "Profile"]

  istrue = true


  lender: any = {
    address: {
      address: undefined, city: undefined, pin: undefined, state: undefined
    }
  }

  constructor(private service : PaymentService,private chat : ChatService, private recommendationService: RecommendationService,private paymentService : PaymentService, private lenderservice: LenderService, private route: Router,private  loanService : LoanService) {


  }

  ngOnInit(): void {


    this.lenderservice.getLenderById(localStorage.getItem('email')).subscribe(data => {

      this.lender = data;
      if (this.lender.creditScore == null) {

        this.add();
      }


      // this.service.LenderCreditScore
      console.log(this.lender.creditScore)
      this.recommendationService.getbBorrower(this.lender.creditScore).subscribe(data => {
        console.log(data)
        this.borrower = data;
      });

    });
    this.loanService.getLenderLoans().subscribe({
      next : data => this.loans = data
    })


  }


  creditScore() {


    this.route.navigate(['/dashboard/profile'])
    this.istrue = true


  }

  add() {


    this.istrue = !this.istrue


  }
  recovered(emi : Array<any>){
    if(emi && emi.length>0)
    return Math.round(emi.reduce((acc:any,emiVal:any)=>emiVal.paymentStatus?acc+emiVal.price:acc+0,0)*10)/10;
    else return 0
  }
  progress(loan : any){
    let totalPaid = this.recovered(loan.emi);
    let percentage = (totalPaid/loan.amount)*100;
    return Math.round(percentage)
  }
  pay(recommendedLoan:any){
    this.service.payment(recommendedLoan.amount, recommendedLoan.borrowerId, localStorage.getItem("email")).subscribe(data => {
      console.log(data);
      let amount = recommendedLoan.amount;
      let that = this;
      let paystatus: any = ""
      if (data.status == "created") {
       let id = data.id;
        let options = {
          key: data.key,
          amount: data.amount,
          currency: data.currency,
          name: recommendedLoan.name,
          description: "transfer",
          order_id: data.id,

          handler: (data: any) => {
            console.log(data.razorpay_payment_id)
            console.log(data.razorpay_order_id)
            console.log(data.razorpay_signature)
            paystatus = "success"


            that.updatedetail(recommendedLoan,paystatus);

            Swal.fire("payment succesfful", "Well done, you  entered amount", "success")

          },


          "prefill": {
            "name": " ",
            "email": " ",
            "contact": " "


          },


          notes: {
            address: "lender address"
          },
          theme: {
            color: "#3399cc"
          },


        };


        let rzp = new Razorpay(options);

        rzp.on('payment.failed', function (response: any) {
          //this.message = "Payment Failed";
          // Todo - store this information in the server
          console.log(response.error.code);
          console.log(response.error.description);
          console.log(response.error.source);
          console.log(response.error.step);
          console.log(response.error.reason);
          console.log(response.error.metadata.order_id);
          console.log(response.error.metadata.payment_id);
          paystatus = "fail"
          that.updatedetail(recommendedLoan,paystatus);

          //this.error = response.error.reason;
        });

        rzp.open();


      }


      //  updatedetail(paystatus="pending")


    });


  }


  updatedetail (recommendedLoan: any, paystatus : any)  {
//   console.log(this.recommendedLoan)
//
  this.service.updatedetail(recommendedLoan.amount, recommendedLoan.borrowerId, localStorage.getItem("email"), recommendedLoan.borrowerId, paystatus, recommendedLoan.id).subscribe(data => {

  });
//
// }
  }
  startChat(borrowerId:any){
    this.chat.createChat(borrowerId).subscribe({
      next :(data:any)=>this.route.navigateByUrl("/dashboard/chat/"+data.id)
    })
  }

}
