import { CompanyService } from './../../services/company.service';
import { AlertsService } from './../../services/alerts.service';
import { async } from '@angular/core/testing';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TokenStorageService } from './../../services/security/token-storage.service';


@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {
  userAlerts = 0;
  hrAlerts = 0;

  constructor(
    private router: Router,
    private tokenStorage: TokenStorageService,
    private alertsService: AlertsService,
    private companyService: CompanyService
  ) { }

  isLogged = this.tokenStorage.isAuthenticated();
  userRole = this.tokenStorage.getRole();

  ngOnInit(): void {
    if (this.isLogged){
      this.checkForAlerts();
    }
  }

  logout() {
    this.tokenStorage.deleteUserFromLocalStorage();
    window.location.reload();
    this.router.navigate(['/home']);
  }

  checkForAlerts(){
    this.alertsService.getUserAlerts().subscribe(res => {
      this.userAlerts = + res;
    });

    console.log(this.companyService.getCurrentCompany());

    if (this.companyService.getCurrentCompany()){
      this.alertsService.getHrAlerts().subscribe(res => {
        this.hrAlerts = + res;
      });
    }
    }
}
