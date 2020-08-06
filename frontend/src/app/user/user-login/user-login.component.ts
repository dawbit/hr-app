import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../security/services/auth.service';

@Component({
  selector: 'app-user-login',
  templateUrl: './user-login.component.html',
  styleUrls: ['./user-login.component.scss']
})
export class UserLoginComponent implements OnInit {

  loginForm: FormGroup;

  constructor(private authService: AuthService,
              private formBuilder: FormBuilder,
              private router: Router) {}

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      username: [''],
      password: ['']
    });
  }

  get controls() { return this.loginForm.controls; }

  onSubmit() {
    this.authService.login(
      {
        username: this.controls.username.value,
        password: this.controls.password.value
      }
    )
      .subscribe(success => {
        if (success) {
          this.router.navigate(['/homepage']);
        }
      });
  }

}
