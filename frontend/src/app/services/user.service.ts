import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { GlobalConstants } from './../common/global-constants';

@Injectable({
  providedIn: 'root'
})

export class UserService {
  private baseUrl = GlobalConstants.apiURL + '/user';
  private httpOptions = GlobalConstants.httpOptions;

  constructor(private http: HttpClient) { }

  // tslint:disable-next-line: ban-types
  createUser(User: Object): Observable<Object> {
    return this.http.post(this.baseUrl + '/register', User);
  }
}
