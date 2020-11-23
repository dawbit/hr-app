import { CompanyService } from '../../../services/company.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-random-company-presentation',
  templateUrl: './random-company-presentation.component.html',
  styleUrls: ['./random-company-presentation.component.scss']
})
export class RandomCompanyPresentationComponent implements OnInit {

  companies: any = [];
  comp: any;

  constructor(
    private companyService: CompanyService
  ) { }

  ngOnInit() {
    this.getAllCompanies();
  }

  getCompanyName(){
    return (this.comp && this.comp.name) ? this.comp.name : null;
  }

  getCompanyAbout(){
    return (this.comp && this.comp.about) ? this.comp.about : null;
  }

  getAllCompanies(){
    this.companyService.getAllCompanies().subscribe(data => {
        for (const key in data){
          if (data.hasOwnProperty(key)){
            this.companies.push({
              id: data[key].id,
              name: data[key].name,
              location: data[key].location,
              about: data[key].about,
              image: data[key].image
            });
          }
        }
        this.comp = this.companies[Math.floor(Math.random() * this.companies.length)];
    });
  }
}
