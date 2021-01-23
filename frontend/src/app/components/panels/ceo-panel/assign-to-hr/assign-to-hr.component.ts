import { CeoService } from './../../../../services/ceo.service';
import { Component, OnInit, ElementRef, HostListener, ViewChild } from '@angular/core';
import { MdbTableDirective, MdbTablePaginationComponent } from 'angular-bootstrap-md';
import { ToastService } from './../../../../services/toast.service';

@Component({
  selector: 'app-assign-to-hr',
  templateUrl: './assign-to-hr.component.html',
  styleUrls: ['./assign-to-hr.component.scss']
})
export class AssignToHrComponent implements OnInit {
  @ViewChild(MdbTableDirective, { static: true }) mdbTable: MdbTableDirective;
  @ViewChild(MdbTablePaginationComponent, { static: true }) mdbTablePagination: MdbTablePaginationComponent;
  @ViewChild('row', { static: true }) row: ElementRef;

  users: any = [];
  headUsers = ['login', 'email', 'firstName', 'surname', 'assign to hr'];
  searchText = '';
  previous: string;
  maxVisibleItems = 10;

  constructor(
    private ceoService: CeoService,
    private toasts: ToastService
  ) { }

  @HostListener('input') oninput() {
    this.mdbTablePagination.searchText = this.searchText;
  }

  ngOnInit() {
    this.getRecruitableUsers();
  }

  getRecruitableUsers() {
    this.ceoService.getRecruitableUsers().subscribe(data => {
      for (const key in data) {
        if (data.hasOwnProperty(key)) {
          this.users.push({
            id: data[key].id,
            login: data[key].login,
            email: data[key].email,
            firstName: data[key].firstName,
            surname: data[key].surname
          });
        }
      }
      this.mdbTable.setDataSource(this.users);
      this.users = this.mdbTable.getDataSource();
      this.previous = this.mdbTable.getDataSource();
    });
  }

  searchItems() {
    const prev = this.mdbTable.getDataSource();

    if (!this.searchText) {
      this.mdbTable.setDataSource(this.previous);
      this.users = this.mdbTable.getDataSource();
    }

    if (this.searchText) {
      this.users = this.mdbTable.searchLocalDataBy(this.searchText);
      this.mdbTable.setDataSource(prev);
    }

    this.mdbTablePagination.calculateFirstItemIndex();
    this.mdbTablePagination.calculateLastItemIndex();

    this.mdbTable.searchDataObservable(this.searchText).subscribe(() => {
      this.mdbTablePagination.calculateFirstItemIndex();
      this.mdbTablePagination.calculateLastItemIndex();
    });
  }

  assignToHr(userId) {
    this.ceoService.addUserToHr(userId).subscribe(res => {
      this.toasts.showSuccess('ceo.assignSuccess');
    },
      err => {
        this.toasts.showError('ceo.assignError');
      }
    );
  }




}
