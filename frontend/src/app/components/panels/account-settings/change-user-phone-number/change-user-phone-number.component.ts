import { UserService } from './../../../../services/user.service';
import { FormGroup, FormBuilder } from '@angular/forms';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-change-user-phone-number',
  templateUrl: './change-user-phone-number.component.html',
  styleUrls: ['./change-user-phone-number.component.scss']
})
export class ChangeUserPhoneNumberComponent implements OnInit {
  changePhoneNumberForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private userService: UserService
  ) { }

  ngOnInit() {
    this.changePhoneNumberForm = this.fb.group({
      password: [''],
      phoneNumber: [''],
    });
  }

  changePhoneNumber(){
    this.userService.changeUserPhone(this.changePhoneNumberForm.value).subscribe(res => {
      console.log('jupi');
    },
    err => {
      console.log(err);
    }
    );
  }
}
