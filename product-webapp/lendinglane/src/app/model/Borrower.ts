export type Borrower = {
  firstName?: string;
  lastName?: string;
  emailId?: string;
  password?: string;
  confirmPassword?: string;
  aadhaarNo?: string;
  panNo?: string;
  phoneNo?: string;
  amount?: number;
  cibilScore?: number;
  aadhar ?: File;
  address:{
  address?: string;
  city?: string;
  pin?: string;
  state?: string;
};
}

