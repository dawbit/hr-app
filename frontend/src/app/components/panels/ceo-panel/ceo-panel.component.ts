import { CompanyService } from './../../../services/company.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-ceo-panel',
  templateUrl: './ceo-panel.component.html',
  styleUrls: ['./ceo-panel.component.scss']
})
export class CeoPanelComponent implements OnInit {

  selectedBookmark: string;
  companyName = '';

  constructor(
    private companyService: CompanyService
  ) { }

  ngOnInit(): void {
    this.getCompanyName();
  }

  showBookmark(bookmark): string {
    return this.selectedBookmark = bookmark;
  }

  getCompanyName(){
    this.companyService.getCurrentCompany().subscribe(res => {
      this.companyName = res;
    });
  }

}
