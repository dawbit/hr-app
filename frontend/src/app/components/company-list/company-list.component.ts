import { CompanyService } from './../../services/company.service';
import { Company } from './company-list-single-element/company';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-company-list',
  templateUrl: './company-list.component.html',
  styleUrls: ['./company-list.component.scss']
})
export class CompanyListComponent implements OnInit {

  company: Company;
  // companies: any[] = [
  //   {
  //     id: 1,
  //     ceo: 'kleo',
  //     about: 'donatan',
  //     name: 'wytwórnia pączków',
  //     location: 'Świebodzin',
  //   },
  //   {
  //     id: 2,
  //     ceo: 'lelleo',
  //     about: 'donatan',
  //     name: 'wytwórnia pączków',
  //     location: 'Świebodzin',
  //   }
  // ];

  constructor(private companyService: CompanyService) { }


  ngOnInit(){

  }

}
