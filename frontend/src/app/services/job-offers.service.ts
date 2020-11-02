import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { GlobalConstants } from './../common/global-constants';
import { Company } from './../classes/company';

@Injectable({
  providedIn: 'root'
})

export class JobOffersService {
  private apiURL = GlobalConstants.apiURL;
  private baseUrl = GlobalConstants.apiURL + '/announcements';
  private httpOptions = GlobalConstants.httpOptions;

  constructor(private http: HttpClient) { }

  addOffer(company: Company): Observable<any> {
    return this.http.post(this.baseUrl + '/add', company, { observe: 'response' });
  }

  getAllOffers(): Observable<any> {
    return this.http.get(this.baseUrl + '/all', this.httpOptions);
  }

  findOffer(searchParams: string): Observable<any> {
    return this.http.get(`${this.baseUrl}/find?q=${searchParams}`);
  }
}
