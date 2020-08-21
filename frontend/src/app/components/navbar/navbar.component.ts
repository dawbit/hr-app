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

  constructor(
    private router: Router,
    private tokenStorage: TokenStorageService
  ) { }

  isLogged = this.tokenStorage.isAuthenticated();
  userRole = this.tokenStorage.getRole();

  ngOnInit(): void {
  }

  logout() {
    this.tokenStorage.deleteUserFromLocalStorage();
    window.location.reload();
    this.router.navigate(['/home']);
  }

}
