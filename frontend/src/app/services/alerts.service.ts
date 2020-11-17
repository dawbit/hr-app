import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { GlobalConstants } from './../common/global-constants';
import { User } from '../classes/user';

@Injectable({
  providedIn: 'root'
})

export class AlertsService {
  private apiURL = GlobalConstants.apiURL;
  private userURL = GlobalConstants.apiURL + '/alerts/user';
  private hrURL = GlobalConstants.apiURL + '/alerts/hr-user';
  private httpOptions = GlobalConstants.httpOptions;

  constructor(private http: HttpClient) { }

  setUserRead(testParticipantId: number): Observable<any>{
    return this.http.post(`${this.userURL}/${testParticipantId}/setAsRead`, {observe: 'response'});
  }

  setHrRead(id: number): Observable<any>{
    return this.http.post(`${this.hrURL}/${id}/setAsRead`, {observe: 'response'});
  }
}
