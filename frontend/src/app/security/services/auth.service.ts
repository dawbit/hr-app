import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { of, Observable, BehaviorSubject } from 'rxjs';
import { catchError, mapTo, tap } from 'rxjs/operators';
import { config } from '../config';
import { Token } from '../models/token';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private readonly JWT_TOKEN = 'JWT_TOKEN';
  private readonly REFRESH_TOKEN = 'REFRESH_TOKEN';

  constructor(private http: HttpClient) {}

  private infoRole = new BehaviorSubject<boolean>(false);
  currentRole = this.infoRole.asObservable();

  private infoLogged = new BehaviorSubject<boolean>(false);
  currentUser = this.infoLogged.asObservable();


  login(user: { username: string, password: string }): Observable<boolean> {
    return this.http.post<any>(`${config.apiUrl}/login`, user)
      .pipe(
        tap(tokens => this.doLoginUser(user.username, tokens)),
        mapTo(true),
        catchError(error => {
          alert(error.error);
          return of(false);
        }));
  }

  logout() {
    this.http.get<any>(`http://localhost:4200/homegape`);
  }

  isLoggedIn() {
    return this.infoLogged.value;
  }

  refreshToken() {
    return this.http.post<any>(`${config.apiUrl}/refresh`, {
      'refreshToken': this.getRefreshToken()
    }).pipe(tap((tokens: Token) => {
      this.storeJwtTokenRole(tokens.jwt);
    }));
  }

  getJwtToken() {
    return localStorage.getItem(this.JWT_TOKEN);
  }

  private doLoginUser(login: string, tokens: Token) {
    this.infoRole.next(tokens.role);
    this.infoLogged.next(true);
    this.storeTokens(tokens);
  }

  doLogoutUser() {
    this.infoLogged.next(false);
    this.removeTokens();
  }

  private getRefreshToken() {
    return localStorage.getItem(this.REFRESH_TOKEN);
  }

  private storeJwtTokenRole(jwt: string) {
    localStorage.setItem(this.JWT_TOKEN, jwt);
  }

  private storeTokens(tokens: Token) {
    localStorage.setItem(this.JWT_TOKEN, tokens.jwt);
    localStorage.setItem(this.REFRESH_TOKEN, tokens.refreshToken);
  }

  private removeTokens() {
    localStorage.removeItem(this.JWT_TOKEN);
    localStorage.removeItem(this.REFRESH_TOKEN);
  }
}
