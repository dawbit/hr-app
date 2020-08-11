import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-login-register',
  templateUrl: './login-register.component.html',
  styleUrls: ['./login-register.component.scss']
})
export class LoginRegisterComponent implements OnInit {

  // TODO
  loginForm: FormGroup;
  registerForm: FormGroup;

  formValidation: FormGroup;
  onBlurSubmitValidation: FormGroup;
  validationRegisterForm: FormGroup;
  selectedForm = 'login';


  constructor(
    private formBuilder: FormBuilder
  ) { }

  ngOnInit(): void {
    // TODO
    this.loginForm = this.formBuilder.group({
      login: [''],
      password: ['']
    });

    this.registerForm = this.formBuilder.group({
      login: [''],
      password: [''],
      fname: [''],
      lname: ['']
    });

    this.registerForm = new FormGroup({
      emailReg: new FormControl(null, [Validators.required, Validators.email]),
      onBlur: new FormControl(null, Validators.required),
      passwordLengthReg: new FormControl(null, [Validators.required, Validators.minLength(6)]),
      fnameReg: new FormControl(null, Validators.required),
      lnameReg: new FormControl(null, Validators.required),
      phoneNumberReg: new FormControl(null, [Validators.required, Validators.minLength(9), Validators.pattern(/^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\s\./0-9]*$/)]),
      LoginReg: new FormControl(null, Validators.required)
    }, { updateOn: 'blur' });

  }

  get onBlur() { return this.registerForm.get('onBlur'); }
  get emailReg() { return this.registerForm.get('emailReg'); }
  get passwordLengthReg() { return this.registerForm.get('passwordLengthReg'); }
  get fnameReg() { return this.registerForm.get('fnameReg'); }
  get lnameReg() { return this.registerForm.get('lnameReg'); }
  get phoneNumberReg() { return this.registerForm.get('phoneNumberReg'); }
  get LoginReg() { return this.registerForm.get('LoginReg'); }
  // get emailConfirmationReg() {return this.registerForm.get('emailConfirmationReg'); }


  switchForm() {
    if (this.selectedForm === 'login') {
      this.selectedForm = 'register';
    }
    else {
      this.selectedForm = 'login';
    }
  }

}
