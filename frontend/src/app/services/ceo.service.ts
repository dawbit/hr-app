import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { GlobalConstants } from './../common/global-constants';
import { Injectable } from '@angular/core';
import { User } from '../classes/user';

@Injectable({
  providedIn: 'root'
})
export class CeoService {

  constructor(private http: HttpClient) { }
  private apiUrl = GlobalConstants.apiURL;
  private baseUrl = GlobalConstants.apiURL + '/ceo-panel';
  private httpOptions = GlobalConstants.httpOptions;

  getRecruitableUsers(): Observable<User[]>{
    return this.http.get<User[]>(`${this.baseUrl}/getusers`, this.httpOptions);
  }

  getHrUsers(): Observable<User[]> {
    return this.http.get<User[]>(`${this.baseUrl}/getusers/our`, this.httpOptions);
  }

  addUserToHr(id: number): Observable<any> {
    return this.http.post(`${this.baseUrl}/hrusers/add`, id, {observe: 'response'});
  }

  deleteUserFromHr(id: number): Observable<any> {
    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
      body: id,
    };

    return this.http.delete(`${this.baseUrl}/hrusers/delete`, options);
  }


}
