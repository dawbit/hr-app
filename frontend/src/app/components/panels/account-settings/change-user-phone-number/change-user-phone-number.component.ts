import { UserService } from './../../../../services/user.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { MustMatch } from 'src/app/helpers/must-match';
import { ToastService } from 'src/app/services/toast.service';

@Component({
  selector: 'app-change-user-phone-number',
  templateUrl: './change-user-phone-number.component.html',
  styleUrls: ['./change-user-phone-number.component.scss']
})
export class ChangeUserPhoneNumberComponent implements OnInit {
  changePhoneNumberForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private toast: ToastService
  ) { }

  ngOnInit() {
    this.changePhoneNumberForm = this.fb.group({
      password: ['', [Validators.required, Validators.minLength(6)]],
      newPhoneNumber: ['', [Validators.required, Validators.minLength(9),
      Validators.pattern(/^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\s\./0-9]*$/)]],
      newPhoneNumberConfirmation: ['', [Validators.required]]
    }, { validator: [MustMatch('newPhoneNumber', 'newPhoneNumberConfirmation')] });
  }

  get form() { return this.changePhoneNumberForm.controls; }

  changePhoneNumber() {
    this.userService.changeUserPhone(this.changePhoneNumberForm.value).subscribe(res => {
      this.toast.showSuccess('message.success');
    },
    err => {
      this.toast.showSuccess('message.error');
    }
    );
  }
}
