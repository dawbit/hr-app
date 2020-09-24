import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { AccountTypesService } from './../../../services/account-types.service';
import { ToastService } from './../../../services/toast.service';

@Component({
  selector: 'app-admin-panel',
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.scss']
})
export class AdminPanelComponent implements OnInit {

  newAccountType: FormGroup;
  users = [];
  selectedBookmark: string;

  constructor(
    private formBuilder: FormBuilder,
    private accountTypes: AccountTypesService,
    private toast: ToastService
  ) { }

  ngOnInit(): void {
    this.newAccountType = this.formBuilder.group({
      roleName: ['']
    });
  }

  showBookmark(bookmark): string {
    return this.selectedBookmark = bookmark;
  }

  addAccountType() {
    const object = this.newAccountType.value;
    object.roleId = (Math.floor(Math.random() * 16) + 5000) * (Math.floor(Math.random() * 16) + 1000) + 2137;
    this.accountTypes.add(object).subscribe(
      res => {
        if (res && res.ok && res.status === 200) {
          this.toast.showSuccess('message.accountTypeAdded');
        }
      },
      err => {
        if (err && err.status === 409) {
          this.toast.showWarning('message.accountTypeAlreadyExist');
        } else {
          this.toast.showError('message.accountTypeNotAdded');
        }
      }
    );
  }

}
