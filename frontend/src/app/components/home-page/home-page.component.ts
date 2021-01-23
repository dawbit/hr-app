import { TokenStorageService } from './../../services/security/token-storage.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.scss']
})
export class HomePageComponent implements OnInit {

  loggedIn = this.tokenStorage.isAuthenticated();

  constructor(
    private tokenStorage: TokenStorageService
  ) { }

  ngOnInit(): void {
  }

}
