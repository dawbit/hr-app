import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, CanActivate } from '@angular/router';
import { JobOffersService } from 'src/app/services/job-offers.service';
import { TokenStorageService } from 'src/app/services/security/token-storage.service';


@Component({
  selector: 'app-job-offers-list',
  templateUrl: './job-offers-list.component.html',
  styleUrls: ['./job-offers-list.component.scss']
})
export class JobOffersListComponent implements OnInit {

  public jobOffers: any = [];
  public jobOfferSearchForm: FormGroup;

  constructor(
    private route: ActivatedRoute,
    private jobOffersService: JobOffersService,
    private formBuilder: FormBuilder,
    private tokenStorageService: TokenStorageService
  ) { }

  ngOnInit() {
    this.getAllJobOffers();
    // this.companyToSearch = this.route.snapshot.params.companyToSearch;
    this.jobOfferSearchForm = this.formBuilder.group({
      jobOfferToSearch: ['', [Validators.required, Validators.minLength(1)]]
    });
  }

  floatingButtonAccess(){
    if (this.tokenStorageService.getRole() === 'ADMIN' || this.tokenStorageService.getRole() === 'HR'
        || this.tokenStorageService.getRole() === 'CEO'){
      return true;
    } else {
      return false;
    }

  }

  getAllJobOffers(){
    this.jobOffersService.getAllOffers().subscribe(data => {
        for (const key in data){
          if (data.hasOwnProperty(key)){
            this.jobOffers.push({
              id: data[key].announcementId,
              title: data[key].announcementTitle,
              description: data[key].announcementDescription,
              companyId: data[key].companyId,
              companyName: data[key].companyName,
              companyLocation: data[key].companyLocation,
              image: data[key].image
            });
          }
        }
    });
  }

  SearchJobOffer(){
    this.jobOffers = [];

    this.jobOffersService.findOffer(this.jobOfferSearchForm.get('jobOfferToSearch').value).subscribe(data => {
      for (const key in data){
        if (data.hasOwnProperty(key)){
          this.jobOffers.push({
            id: data[key].announcementId,
            title: data[key].announcementTitle,
            description: data[key].announcementDescription,
            companyId: data[key].companyId,
            companyName: data[key].companyName,
            companyLocation: data[key].companyLocation,
            image: data[key].image
          });
        }
      }
  });
  }


}
