import { UserService } from './../../../../services/user.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { MustMatch } from 'src/app/helpers/must-match';

@Component({
  selector: 'app-change-user-email',
  templateUrl: './change-user-email.component.html',
  styleUrls: ['./change-user-email.component.scss']
})
export class ChangeUserEmailComponent implements OnInit {
  changeEmailForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private userService: UserService
  ) { }

  ngOnInit() {
    this.changeEmailForm = this.fb.group({
      password: ['', [Validators.required]],
      newEmail: ['', [Validators.required, Validators.email]],
      newEmailConfirmation: ['', [Validators.required, Validators.email]]
    }, { validator: [MustMatch('newEmail', 'newEmailConfirmation')] });
  }


  get form() { return this.changeEmailForm.controls; }

  changeEmail(){
    this.userService.changeUserEmail(this.changeEmailForm.value).subscribe(res => {
      console.log('jupi');
    },
    err => {
      console.log(err);
    }
    );
  }

}
