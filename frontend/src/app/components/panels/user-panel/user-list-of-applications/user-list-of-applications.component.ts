import { AlertsService } from './../../../../services/alerts.service';
import { UserService } from './../../../../services/user.service';
import { ChangeDetectorRef, Component, OnInit, AfterViewInit, ElementRef, ViewChild, HostListener } from '@angular/core';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { MdbTableDirective, MdbTablePaginationComponent } from 'angular-bootstrap-md';
import { ToastService } from 'src/app/services/toast.service';

@Component({
  selector: 'app-user-list-of-applications',
  templateUrl: './user-list-of-applications.component.html',
  styleUrls: ['./user-list-of-applications.component.scss']
})
export class UserListOfApplicationsComponent implements OnInit, AfterViewInit {
  @ViewChild(MdbTableDirective, { static: true }) mdbTable: MdbTableDirective;
  @ViewChild(MdbTablePaginationComponent, { static: true }) mdbTablePagination: MdbTablePaginationComponent;
  @ViewChild('row', { static: true }) row: ElementRef;

  headers = [this.translate.instant('user.companyName'), this.translate.instant('user.announcementName'),
              this.translate.instant('user.quizCode'), this.translate.instant('user.status')];

  applications: any = [];
  searchText = '';
  previous: string;

  maxVisibleItems = 10;

  constructor(
    private cdRef: ChangeDetectorRef,
    private userService: UserService,
    private alertsService: AlertsService,
    private router: Router,
    private translate: TranslateService,
    private toast: ToastService
  ) { }

  @HostListener('input') oninput() {
    this.mdbTablePagination.searchText = this.searchText;
  }

  ngOnInit() {
    this.getAllUserApplications();
  }

  ngAfterViewInit(){
    this.mdbTablePagination.setMaxVisibleItemsNumberTo(this.maxVisibleItems);
    this.mdbTablePagination.calculateFirstItemIndex();
    this.mdbTablePagination.calculateLastItemIndex();
    this.cdRef.detectChanges();
  }

  getAllUserApplications(){
    this.userService.getAllApplications().subscribe(res => {
      for (const key in res){
        if (res.hasOwnProperty(key)){
          this.applications.push({
            testParticipantId: res[key].testParticipantId,
            companyName: res[key].companyName,
            announcementName: res[key].announcementName,
            quizCode: res[key].quizCode,
            completed: res[key].completed
          });
        }
      }
      this.mdbTable.setDataSource(this.applications);
      this.applications = this.mdbTable.getDataSource();
      this.previous = this.mdbTable.getDataSource();
    });
  }

  searchItems() {
    const prev = this.mdbTable.getDataSource();

    if (!this.searchText) {
      this.mdbTable.setDataSource(this.previous);
      this.applications = this.mdbTable.getDataSource();
    }

    if (this.searchText) {
      this.applications = this.mdbTable.searchLocalDataBy(this.searchText);
      this.mdbTable.setDataSource(prev);
    }

    this.mdbTablePagination.calculateFirstItemIndex();
    this.mdbTablePagination.calculateLastItemIndex();

    this.mdbTable.searchDataObservable(this.searchText).subscribe(() => {
      this.mdbTablePagination.calculateFirstItemIndex();
      this.mdbTablePagination.calculateLastItemIndex();
    });
  }

  setAsRead(testParticipantId){
    this.alertsService.setUserRead(testParticipantId).subscribe(res => {
    });
  }

}
