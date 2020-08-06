import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-login-register',
  templateUrl: './login-register.component.html',
  styleUrls: ['./login-register.component.scss']
})
export class LoginRegisterComponent implements OnInit {

  // TODO
  loginForm: FormGroup;
  registerForm: FormGroup;

  selectedForm = 'login';

  constructor(
    private formBuilder: FormBuilder
  ) { }

  ngOnInit(): void {
    // TODO
    this.loginForm = this.formBuilder.group({
      username: [''],
      password: ['']
    });

    this.registerForm = this.formBuilder.group({
      login: [''],
      password: [''],
      fname: [''],
      lname: ['']
    });
  }

  switchS() {
    if (this.selectedForm === 'login') {
      this.selectedForm = 'register';
    }
    else {
      this.selectedForm = 'login';
    }
  }

}
