import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from './security/services/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'HR app';
  isAdmin: boolean = false;
  isLogged: boolean = false;
  isNotLogged: boolean = !this.isLogged;

  constructor(private authService: AuthService,
    private router: Router){}

  ngOnInit(){
    this.authService.currentRole.subscribe(message => this.isAdmin = message);
    this.authService.currentUser.subscribe(message => this.isLogged = message);
    this.authService.currentUser.subscribe(message => this.isNotLogged = !message);
  }

  logout() {
    this.router.navigate(['/homepage']);
    this.authService.doLogoutUser();
    this.isAdmin = false;
  }
}
