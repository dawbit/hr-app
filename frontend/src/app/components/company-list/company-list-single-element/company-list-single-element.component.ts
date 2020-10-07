import { Company } from './company';
import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-company-list-single-element',
  templateUrl: './company-list-single-element.component.html',
  styleUrls: ['./company-list-single-element.component.scss']
})
export class CompanyListSingleElementComponent implements OnInit {

  @Input() company: Company;

  constructor() { }

  ngOnInit(): void {
    this.company = new Company();
    this.company.id = 1;
    this.company.ceo = 'kleo';
    this.company.about = 'donatan';
    this.company.name = 'wytwórnia pączków';
    this.company.location = 'Świebodzin';
  }

}
