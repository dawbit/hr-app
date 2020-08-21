import { Injectable } from '@angular/core';
// import { JwtModule, JwtHelperService } from '@auth0/angular-jwt';
import { JwtHelperService } from '@auth0/angular-jwt';
import decode from 'jwt-decode';

const TOKEN_KEY = 'auth-token';
const USER_KEY = 'auth-user';
const ROLE_KEY = 'auth-role';

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {
  public jwtHelper: JwtHelperService = new JwtHelperService();

  constructor() { }

  signOut() {
    window.localStorage.clear();
  }

  public saveToken(token: string) {
    window.localStorage.removeItem(TOKEN_KEY);
    window.localStorage.setItem(TOKEN_KEY, token);
  }

  public getToken(): string {
    return localStorage.getItem(TOKEN_KEY);
  }

  public saveUser(user: string) {
    window.localStorage.removeItem(USER_KEY);
    window.localStorage.setItem(USER_KEY, user);
  }

  public getUser() {
    return JSON.parse(localStorage.getItem(USER_KEY));
  }

  public saveRole(role: string) {
    window.localStorage.removeItem(ROLE_KEY);
    window.localStorage.setItem(ROLE_KEY, role);
  }

  public getRole(): string {
    return localStorage.getItem(ROLE_KEY);
  }

  public saveUserInLocalStorage(token) {
    if (token) {
      const decodedToken = decode(token);
      this.saveToken(token);
      this.saveUser(decodedToken.sub);
      this.saveRole(decodedToken.role);
    }
  }

  public deleteUserFromLocalStorage() {
    window.localStorage.removeItem(TOKEN_KEY);
    window.localStorage.removeItem(USER_KEY);
    window.localStorage.removeItem(ROLE_KEY);
  }

  public isAuthenticated(): boolean {
    const jwtHelper = new JwtHelperService();
    const token = localStorage.getItem(TOKEN_KEY);

    // Check if the token is expired and return true or false
    console.log(!this.jwtHelper.isTokenExpired(token));
    return !this.jwtHelper.isTokenExpired(token);

  }

}
