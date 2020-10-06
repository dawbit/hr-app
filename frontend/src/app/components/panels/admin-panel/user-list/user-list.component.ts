import { Component, OnInit, ElementRef, HostListener, AfterViewInit, ViewChild, ChangeDetectorRef } from '@angular/core';
import { MdbTableDirective, MdbTablePaginationComponent } from 'angular-bootstrap-md';
import { UserService } from '../../../../services/user.service';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss']
})
export class UserListComponent implements OnInit, AfterViewInit {
  @ViewChild(MdbTableDirective, { static: true }) mdbTable: MdbTableDirective;
  @ViewChild(MdbTablePaginationComponent, { static: true }) mdbTablePagination: MdbTablePaginationComponent;
  @ViewChild('row', { static: true }) row: ElementRef;

  users: any = [];
  headUsers = ['id', 'login', 'email', 'firstName', 'surname', 'Actions'];

  searchText = '';
  previous: string;

  maxVisibleItems = 10;

  constructor(
    private cdRef: ChangeDetectorRef,
    private userService: UserService,
    private router: Router
  ) { }

  @HostListener('input') oninput() {
    this.mdbTablePagination.searchText = this.searchText;
  }

  ngOnInit() {
    this.getAllUsers();
  }

  ngAfterViewInit() {
    this.mdbTablePagination.setMaxVisibleItemsNumberTo(this.maxVisibleItems);
    this.mdbTablePagination.calculateFirstItemIndex();
    this.mdbTablePagination.calculateLastItemIndex();
    this.cdRef.detectChanges();
  }

  getAllUsers() {
    this.userService.getAllUsers().subscribe(data => {
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

  deleteUser(id: number) {
    this.userService.deleteUser(id).subscribe();
  }

  userDetails(id: number) {
    this.router.navigate([this.router.url + '/details', id]);
    console.log('Detale');
  }

  updateUser(id: number) {
    this.router.navigate([this.router.url + '/update', id]);
    console.log('apdejt');
  }
}
