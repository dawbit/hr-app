import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TokenStorageService } from './../../services/token-storage.service';

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

  ngOnInit(): void {
  }

  logout() {
    this.tokenStorage.deleteUserFromLocalStorage();
    this.router.navigate(['/home']);
  }

}
