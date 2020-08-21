import { AccountTypes } from './../../classes/account-types';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MustMatch } from 'src/app/helpers/must-match';
import { UserService } from './../../services/user.service';
import { User } from './../../classes/user';
import { TokenStorageService } from './../../services/security/token-storage.service';

@Component({
  selector: 'app-login-register',
  templateUrl: './login-register.component.html',
  styleUrls: ['./login-register.component.scss']
})
export class LoginRegisterComponent implements OnInit {

  loginForm: FormGroup;
  registerForm: FormGroup;

  formValidation: FormGroup;
  onBlurSubmitValidation: FormGroup;
  validationRegisterForm: FormGroup;
  selectedForm = 'login';

  user: User = new User();

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private tokenStorage: TokenStorageService
  ) { }

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      login: [''],
      password: ['']
    });

    this.registerForm = this.formBuilder.group({
      emailReg: ['', [Validators.required, Validators.email]],
      emailConfirmReg: ['', [Validators.required, Validators.email]],
      passwordLengthReg: ['', [Validators.required, Validators.minLength(6)]],
      passwordConfirmReg: ['', [Validators.required, Validators.minLength(6)]],
      fnameReg: ['', Validators.required],
      lnameReg: ['', Validators.required],
      phoneNumberReg: ['', [Validators.required, Validators.minLength(9),
      Validators.pattern(/^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\s\./0-9]*$/)]
      ],
      LoginReg: ['', Validators.required]
    }, { validator: [MustMatch('passwordLengthReg', 'passwordConfirmReg'), MustMatch('emailReg', 'emailConfirmReg')] });

  }

  // convenience getter for easy access to form fields
  get f() { return this.registerForm.controls; }


  switchForm() {
    if (this.selectedForm === 'login') {
      this.selectedForm = 'register';
    }
    else {
      this.selectedForm = 'login';
    }
  }

  loginSubmit() {
    this.userService.login(this.loginForm.value).subscribe(res => {
      if (res && res.ok && res.status === 200) {
        const authorizationInfo = res.headers.get('authorization');
        this.tokenStorage.saveUserInLocalStorage(authorizationInfo);
      }
    });
  }

  registerSubmit() {
    this.user.fk_userAccountTypes = new AccountTypes();
    this.user.fk_userAccountTypes.id = 1; // tymczasowo admin

    this.userService.register(this.user).subscribe(res => {
      if (res && res.ok && res.status === 200) {
        // TODO
      }
    });
  }
}
