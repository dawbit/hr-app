import { Component, OnInit } from '@angular/core';
import { JobOffersService } from './../../services/job-offers.service';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ToastService } from '../../services/toast.service';

@Component({
  selector: 'app-job-offers-add',
  templateUrl: './job-offers-add.component.html',
  styleUrls: ['./job-offers-add.component.scss']
})
export class JobOffersAddComponent implements OnInit {

  offerForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private jobOffersService: JobOffersService,
    private toast: ToastService
  ) { }

  ngOnInit(): void {
    this.offerForm = this.formBuilder.group({
      announcementTitle: [''],
      announcementDescription: ['']
    });
  }

  registerCompany() {
    this.jobOffersService.addOffer(this.offerForm.value).subscribe(
      res => {
        if (res && res.ok && res.status === 200) {
          this.toast.showSuccess('message.jobOfferAdded');
        }
      },
      err => {
        this.toast.showError('message.jobOfferAdded');
      }
    );
  }
}


