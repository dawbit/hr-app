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
  private httpOptions = GlobalConstants.httpOptions;

  sendMessage(value: any): Observable<any> {
    return this.http.post(`${this.apiURL}/email/send`, value, { observe: 'response' });
  }

  mailingList(): Observable<any> {
    return this.http.get(`${this.apiURL}/user/mailing`, this.httpOptions);
  }

  saveMailingList(id: number, mailingNewQuiz: boolean): Observable<any> {
    return this.http.put(`${this.apiURL}/user/mailing/edit`, { id, mailingNewQuiz }, { observe: 'response' });
  }

}
