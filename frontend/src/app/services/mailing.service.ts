import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { GlobalConstants } from '../common/global-constants';

@Injectable({
  providedIn: 'root'
})
export class MailingService {

  constructor(private http: HttpClient) { }

  private apiURL = GlobalConstants.apiURL;

  sendMessage(value: any): Observable<any> {
    return this.http.post(`${this.apiURL}/email/send`, value, { observe: 'response' });
  }
}
