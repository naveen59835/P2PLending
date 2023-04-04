import { Component, Input, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { RecommendedBorrower } from '../model/RecommendedBorroer';
import { PaymentService } from '../service/payment.service';
declare var Razorpay: any;
@Component({
  selector: 'app-lender',
  templateUrl: './lender.component.html',
  styleUrls: ['./lender.component.css']
})
export class LenderComponent implements OnInit {
  
  @Input() borrowerRecommended:RecommendedBorrower={};

  showFiller = false;
  constructor(private service:PaymentService,private _snackBar:MatSnackBar) { }

amount:string="111";
toname:any="borrower";
tonamemail:any="borrowe@borwer.co"
tonum:any="9991231231"

paymentstatusfail:any
id:any
fromname=localStorage.getItem('email')
  ngOnInit(): void {
  }




 
 

  pay()
  {



    console.log(this.amount)
   if(this.amount!='')
   {
  

  this.service.payment(this.amount,this.toname,this.fromname).subscribe(data=>{
    console.log(data);
    let paystatus:any=""
 
 
  
    if(data.status=="created")
    {
  
     
      this.id=data.id;
  
  
      let options={
        key:data.key,
        amount:data.amount,
        currency:data.currency,
        name:this.borrowerRecommended.name,
        description:"transfer",
        order_id:data.id,
       
        handler:(data:any)=>{
          console.log(data.razorpay_payment_id)
          console.log(data.razorpay_order_id)
          console.log(data.razorpay_signature)
         paystatus="success"
      
        
          updatedetail(paystatus);
          
          Swal.fire("payment succesfful", "Well done, you  entered amount", "success")
        
        },
   
  
        "prefill":{
          "name": " ",
          "email":  " ",
          "contact": " "
         

        
      },
  
       
  
        notes:{
          address:"lender address"
        },
        theme:{
          color:"#3399cc"
        },
  
  
       
      };
      
  
     
  
      let rzp=new Razorpay(options);
      
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
        paystatus="fail"
        updatedetail(paystatus);
      
        //this.error = response.error.reason;
      });
  
      rzp.open();
  
   
      
   
    }
  
  
 
    
   
  //  updatedetail(paystatus="pending")
   
 
  });
  
  

  
   }
   else{
    // Swal.fire("Success Message Title", "Well done, you pressed a button", "failure")
  
    
      Swal.fire("amount required", " you have not entered amount", "error")



      
    
   }
  


  
  const updatedetail = (paystatus:any) =>
  {
  
    this.service.updatedetail(this.amount,this.borrowerRecommended.name,this.fromname,this.id,paystatus).subscribe(data=>{
  
    });
  
  }
  
  }
  





}
