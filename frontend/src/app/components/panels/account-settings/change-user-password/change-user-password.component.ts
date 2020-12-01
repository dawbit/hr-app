import { UserService } from './../../../../services/user.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { MustMatch } from 'src/app/helpers/must-match';
import { ToastService } from 'src/app/services/toast.service';

@Component({
  selector: 'app-change-user-password',
  templateUrl: './change-user-password.component.html',
  styleUrls: ['./change-user-password.component.scss']
})
export class ChangeUserPasswordComponent implements OnInit {
  changePasswordForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private toast: ToastService
  ) { }


  ngOnInit() {
    this.changePasswordForm = this.fb.group({
      password: ['', [Validators.required, Validators.minLength(6)]],
      newPassword: ['', [Validators.required, Validators.minLength(6)]],
      newPasswordConfirmation: ['', [Validators.required]]
    }, { validator: [MustMatch('newPassword', 'newPasswordConfirmation')] });
  }

  get form() { return this.changePasswordForm.controls; }

  changePassword() {
    this.userService.changeUserPassword(this.changePasswordForm.value).subscribe(res => {
      this.toast.showSuccess('message.success');
    },
    err => {
      this.toast.showSuccess('message.error');
    }
    );
  }
}
