import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {AbstractControl, FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {MatSnackBar} from '@angular/material/snack-bar';
import {Borrower} from '../model/Borrower';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {


  emailFormControl = new FormControl('', [Validators.required, Validators.email]);
  profileForm: FormGroup;
  empl: Borrower | undefined;
  employee = {} as Borrower;
  @Output()
  addEmployee: EventEmitter<Borrower> = new EventEmitter<Borrower>();
  user = {}

  constructor(private fb: FormBuilder, private _snackBar: MatSnackBar) {
    this.profileForm = this.fb.group({

      name: new FormControl('', [Validators.required, Validators.minLength(3)]),
      phone: new FormControl('', [Validators.required, Validators.pattern(/^[7-9]\d{9}$/), Validators.minLength(10)]),
      password: new FormControl('', [Validators.required, Validators.minLength(8), Validators.pattern(/^(?=.*\d)(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z]).{8,}$/)]),
      userId: new FormControl('', [Validators.required, Validators.minLength(3)]),
    });
  }

  get name() {
    return this.profileForm.get("name");
  }

  get email() {
    return this.profileForm.get("email");
  }

  get password() {
    return this.profileForm.get("password");
  }

  get userId() {
    return this.profileForm.get("userId");
  }


  get phoneNo() {
    return this.profileForm.get("phoneNo");
  }

  ngOnInit(): void {
  }

  openSnack(message : string, action : string) {

    this._snackBar.open(message, action, {
      duration: 5000,
      panelClass: ['mat-toolbar', 'mat-primary']
    })

  }


  onSubmit() {
    console.log(this.profileForm.value);
    this.employee = this.profileForm.value;
    this.addEmployee.emit(this.employee);
    if(this.profileForm.valid){
      this.openSnack("Form Submitted Successfully","Success");
      this.profileForm.reset();
    }
    else{
      this.openSnack("Couldn't Submit the form","Failure");
    }

  }


}

