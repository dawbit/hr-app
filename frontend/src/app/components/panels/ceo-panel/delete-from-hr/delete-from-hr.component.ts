import { Component, ElementRef, HostListener, OnInit, ViewChild } from '@angular/core';
import { MdbTableDirective, MdbTablePaginationComponent } from 'angular-bootstrap-md';
import { CeoService } from 'src/app/services/ceo.service';
import { ToastService } from './../../../../services/toast.service';

@Component({
  selector: 'app-delete-from-hr',
  templateUrl: './delete-from-hr.component.html',
  styleUrls: ['./delete-from-hr.component.scss']
})
export class DeleteFromHrComponent implements OnInit {
  @ViewChild(MdbTableDirective, { static: true }) mdbTable: MdbTableDirective;
  @ViewChild(MdbTablePaginationComponent, { static: true }) mdbTablePagination: MdbTablePaginationComponent;
  @ViewChild('row', { static: true }) row: ElementRef;

  hrUsers: any = [];
  headUsers = ['login', 'email', 'firstName', 'surname', 'assign to hr'];
  searchText = '';
  previous: string;
  maxVisibleItems = 10;

  constructor(
    private ceoService: CeoService,
    private toasts: ToastService
  ) { }

  ngOnInit() {
    this.getHrUsers();
  }

  @HostListener('input') oninput() {
    this.mdbTablePagination.searchText = this.searchText;
  }

  getHrUsers() {
    this.ceoService.getHrUsers().subscribe(data => {
      for (const key in data) {
        if (data.hasOwnProperty(key)) {
          this.hrUsers.push({
            id: data[key].id,
            login: data[key].login,
            email: data[key].email,
            firstName: data[key].firstName,
            surname: data[key].surname
          });
        }
      }
      this.mdbTable.setDataSource(this.hrUsers);
      this.hrUsers = this.mdbTable.getDataSource();
      this.previous = this.mdbTable.getDataSource();
    });
  }

  searchItems() {
    const prev = this.mdbTable.getDataSource();

    if (!this.searchText) {
      this.mdbTable.setDataSource(this.previous);
      this.hrUsers = this.mdbTable.getDataSource();
    }

    if (this.searchText) {
      this.hrUsers = this.mdbTable.searchLocalDataBy(this.searchText);
      this.mdbTable.setDataSource(prev);
    }

    this.mdbTablePagination.calculateFirstItemIndex();
    this.mdbTablePagination.calculateLastItemIndex();

    this.mdbTable.searchDataObservable(this.searchText).subscribe(() => {
      this.mdbTablePagination.calculateFirstItemIndex();
      this.mdbTablePagination.calculateLastItemIndex();
    });
  }

  deleteFromHr(userId) {
    this.ceoService.deleteUserFromHr(userId).subscribe(res => {
      this.toasts.showSuccess('ceo.assignSuccess');
      this.hrUsers = this.mdbTable.getDataSource();
    },
      err => {
        this.toasts.showError('ceo.assignError');
      }
    );
  }
}
