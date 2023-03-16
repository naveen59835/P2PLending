

export type Lender = {
    firstName?: any;
    emailId?: any;
    lastName?: any;
    phoneNumber?:any;
    password?: any;
    confirmPassword?: any;
    pan?: any;
    aadhaar?: any;
    address:{
        address?: string;
        city?: string;
        pin?: string;
        state?: string;
    };
    amountToInvest?: any;
    loanDuration?: any;
    creditScore?: any;
    investedAmount?: any;
    availableBalance?: any;
    interestRate?: any;

    

    
}

export type Address= {
    address?: string;
    city?: string;
    pin?: string;
    state?: string;
  }


