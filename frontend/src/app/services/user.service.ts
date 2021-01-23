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

  getUser(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/getUser/${id}`);
  }

  updateUser(id: number, value: any): Observable<object> {
    return this.http.put(`${this.baseUrl}/edituser`, value);
  }

  deleteUser(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/deleteuser/${id}`, {
      responseType: 'text'
    });
  }

  getAllApplications(): Observable<any>{
    return this.http.get(`${this.baseUrl}/list-of-applications`);
  }

  changeUserPassword(passwordData: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/change-password`, passwordData, { observe: 'response' });
  }

  changeUserEmail(emailData: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/change-email`, emailData, { observe: 'response' });
  }

  changeUserPhone(phoneData: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/change-phonenumber`, phoneData, { observe: 'response' });
  }

}
