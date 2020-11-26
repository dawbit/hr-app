import { UserService } from './../../../../services/user.service';
import { FormGroup, FormBuilder } from '@angular/forms';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-change-user-password',
  templateUrl: './change-user-password.component.html',
  styleUrls: ['./change-user-password.component.scss']
})
export class ChangeUserPasswordComponent implements OnInit {
  changePasswordForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private userService: UserService
  ) { }

  ngOnInit() {
    this.changePasswordForm = this.fb.group({
      password: [''],
      newPassword: [''],
    });
  }

  changePassword(){
    this.userService.changeUserPassword(this.changePasswordForm.value).subscribe(res => {
      console.log('jupi');
    },
    err => {
      console.log(err);
    }
    );
  }
}
