import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { GlobalConstants } from './../common/global-constants';
import { AccountTypes } from './../classes/account-types';

@Injectable({
  providedIn: 'root'
})

export class AccountTypesService {
  private apiURL = GlobalConstants.apiURL;
  private baseUrl = GlobalConstants.apiURL + '/accounttype';
  private httpOptions = GlobalConstants.httpOptions;

  constructor(private http: HttpClient) { }

  add(input): Observable<any> {
    return this.http.post(this.baseUrl + '/add', input, { observe: 'response' });
  }
}
