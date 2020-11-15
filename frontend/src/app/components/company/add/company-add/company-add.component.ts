import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { CompanyService } from './../../../../services/company.service';
import { ToastService } from './../../../../services/toast.service';

@Component({
  selector: 'app-company-add',
  templateUrl: './company-add.component.html',
  styleUrls: ['./company-add.component.scss']
})
export class CompanyAddComponent implements OnInit {

  lat;
  lng;
  zoom;
  origin;
  destination;

  companyForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private companyService: CompanyService,
    private toast: ToastService
  ) { }

  ngOnInit(): void {
    this.companyForm = this.formBuilder.group({
      name: [''],
      location: [''],
      about: ['']
    });
  }

  registerCompany() {
    this.companyService.add(this.companyForm.value).subscribe(
      res => {
        if (res && res.ok && res.status === 200) {
          this.toast.showSuccess('message.companyAdded');
        }
      },
      err => {
        this.toast.showError('message.companyNotAdded');
      }
    );
  }

  getUserLocation() {
    // get Users current position

    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(position => {
        this.lat = position.coords.latitude;
        this.lng = position.coords.longitude;
        this.zoom = 16;
        console.log('position', position);
      });
    }
  }

  async getDirection() {

    if (typeof this.lat === 'undefined' || typeof this.lng === 'undefined' || typeof this.zoom === 'undefined') {
      await this.getUserLocation();
    }
    this.origin = { lat: this.lat, lng: this.lng };

    this.destination = { lat: 24.799524, lng: 120.975017 };
    console.log(this.origin);

  }

}
