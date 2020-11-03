import { JobOffersService } from 'src/app/services/job-offers.service';
import { Component, OnInit, ElementRef, HostListener, AfterViewInit, ViewChild, ChangeDetectorRef } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import Swal from 'sweetalert2/dist/sweetalert2.js';
import { MdbTableDirective, MdbTablePaginationComponent } from 'angular-bootstrap-md';
import { Router, ActivatedRoute } from '@angular/router';
import { ToastService } from './../../../../services/toast.service';

@Component({
  selector: 'app-list-of-applications',
  templateUrl: './list-of-applications.component.html',
  styleUrls: ['./list-of-applications.component.scss']
})
export class ListOfApplicationsComponent implements OnInit, AfterViewInit {
  @ViewChild(MdbTableDirective, { static: true }) mdbTable: MdbTableDirective;
  @ViewChild(MdbTablePaginationComponent, { static: true }) mdbTablePagination: MdbTablePaginationComponent;
  @ViewChild('row', { static: true }) row: ElementRef;

  applications: any = [];
  headers = ['alertId', 'announcementId', 'userId', 'userLogin', 'quizId', 'quizName', 'quizCode'];

  searchText = '';
  previous: string;

  maxVisibleItems = 10;

  constructor(
    private cdRef: ChangeDetectorRef,
    private jobOffersService: JobOffersService,
    private router: Router,
    private translate: TranslateService,
    private toast: ToastService
  ) { }

  @HostListener('input') oninput() {
    this.mdbTablePagination.searchText = this.searchText;
  }

  ngOnInit() {
    this.getAllApplications();
  }

  ngAfterViewInit() {
    this.mdbTablePagination.setMaxVisibleItemsNumberTo(this.maxVisibleItems);
    this.mdbTablePagination.calculateFirstItemIndex();
    this.mdbTablePagination.calculateLastItemIndex();
    this.cdRef.detectChanges();
  }

  getAllApplications(){
    this.jobOffersService.getAllApplications().subscribe(data => {
      for (const key in data){
        if (data.hasOwnProperty(key)){
          this.applications.push({
            alertId: data[key].alertId,
            announcementId: data[key].announcementId,
            userId: data[key].simplyUserDto.id,
            userLogin: data[key].simplyUserDto.login,
            quizId: data[key].simplyQuizInfoDto.id,
            quizName: data[key].simplyQuizInfoDto.name,
            quizCode: data[key].simplyQuizInfoDto.code
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

  assignQuiz(alertId) {
    // this.jobOffersService.assignQuiz(alertId, ).subscribe(
    //   res => {
    //     if (res && res.ok && res.status === 200) {
    //       this.toast.showSuccess('message.companyAdded');
    //     }
    //   },
    //   err => {
    //     this.toast.showError('message.companyNotAdded');
    //   }
    // );
  }

}
