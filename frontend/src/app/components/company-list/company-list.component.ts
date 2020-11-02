import { CompanyService } from './../../services/company.service';
import { Company } from './company-list-single-element/company';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-company-list',
  templateUrl: './company-list.component.html',
  styleUrls: ['./company-list.component.scss']
})
export class CompanyListComponent implements OnInit {

  public companies: any = [];
  public companySearchForm: FormGroup;
  // public companyToSearch: string;

  constructor(
    private route: ActivatedRoute,
    private companyService: CompanyService,
    private formBuilder: FormBuilder
    ) {}

  ngOnInit(){
    this.getAllCompanies();
    // this.companyToSearch = this.route.snapshot.params.companyToSearch;
    this.companySearchForm = this.formBuilder.group({
      companyToSearch: ['', [Validators.required, Validators.minLength(1)]]
    });
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
    });
  }

  SearchCompany(){
    this.companies = [];

    this.companyService.getCompany(this.companySearchForm.get('companyToSearch').value).subscribe(
      data =>{
        for (const key in data){
          if (data.hasOwnProperty(key)){ // jeśli nie jest pusta
            this.companies.push({
              id: data[key].id,
              name: data[key].name,
              location: data[key].location,
              about: data[key].about,
              image: data[key].image
            });
          }
        }
      }
    );
  }

}
