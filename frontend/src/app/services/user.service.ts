import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { GlobalConstants } from './../common/global-constants';
import { User } from '../classes/user';

@Injectable({
  providedIn: 'root'
})

export class UserService {
  private apiURL = GlobalConstants.apiURL;
  private baseUrl = GlobalConstants.apiURL + '/user';
  private httpOptions = GlobalConstants.httpOptions;

  constructor(private http: HttpClient) { }

  login(credentials): Observable<any> {
    return this.http.post(this.apiURL + '/login', {
      login: credentials.login,
      password: credentials.password
    }, { observe: 'response' });
  }

  register(user: User): Observable<any> {
    return this.http.post(this.baseUrl + '/register', user, { observe: 'response' });
  }

  getAllUsers(): Observable<any> {
    return this.http.get(this.baseUrl + '/getall', this.httpOptions);
  }

}
