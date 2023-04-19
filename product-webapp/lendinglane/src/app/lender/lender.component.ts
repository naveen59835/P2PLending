import {Component, Input, OnInit} from '@angular/core';
import {MatSnackBar} from '@angular/material/snack-bar';
import {Router} from '@angular/router';
import Swal from 'sweetalert2';
import {RecommendedBorrower} from '../model/RecommendedBorroer';
import {PaymentService} from '../service/payment.service';
import {ChatService} from "../service/chat.service";

declare let Razorpay: any;

@Component({
  selector: 'app-lender',
  templateUrl: './lender.component.html',
  styleUrls: ['./lender.component.css']
})
export class LenderComponent implements OnInit {

  @Input() borrowerRecommended: RecommendedBorrower = {};

  amount:string=" "

  paymentstatusfail: any
  id: any
  fromname = localStorage.getItem('email')

  constructor(private service: PaymentService, private _snackBar: MatSnackBar, private chatService: ChatService, private route: Router) {
  }

  ngOnInit(): void {
  }


  pay(borrowerRecommended: RecommendedBorrower) {
    if (borrowerRecommended.amount!= '') {
      this.service.payment(borrowerRecommended.amount,borrowerRecommended.borrowerId, this.fromname).subscribe(data => {
        console.log(data);
        this.amount = borrowerRecommended.amount;
        let paystatus: any = ""
        if (data.status == "created") {
          this.id = data.id;
          let options = {
            key: data.key,
            amount: data.amount,
            currency: data.currency,
            name: this.borrowerRecommended.borrowerId,

            description: "transfer",
            order_id: data.id,

            handler: (data: any) => {
              console.log(data.razorpay_payment_id)
              console.log(data.razorpay_order_id)
              console.log(data.razorpay_signature)
              paystatus = "success"


              updatedetail(paystatus);

              Swal.fire("payment Successful", "Well done", "success")

            },


            "prefill": {
              "name": "Lending Lane",
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
            updatedetail(paystatus);

          });

          rzp.open();


        }





      });


    } else {



      Swal.fire("amount required", " you have not entered amount", "error")


    }


    const updatedetail = (paystatus: any) => {


      this.service.updatedetail(this.borrowerRecommended.amount, this.borrowerRecommended.borrowerId, this.fromname, this.id, paystatus, this.borrowerRecommended.id).subscribe(data => {

      });

    }

  }

  startChat(borrowerId: any) {
    this.chatService.createChat(borrowerId).subscribe({
      next: () => this.route.navigateByUrl("/dashboard/chat")
    })
  }


}
