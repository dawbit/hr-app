import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { GlobalConstants } from './../common/global-constants';
import { Company } from './../classes/company';

@Injectable({
  providedIn: 'root'
})

export class CompanyService {
  private apiURL = GlobalConstants.apiURL;
  private baseUrl = GlobalConstants.apiURL + '/companies';
  private httpOptions = GlobalConstants.httpOptions;

  constructor(private http: HttpClient) { }

  add(company: Company): Observable<any> {
    return this.http.post(this.baseUrl + '/add', company, { observe: 'response' });
  }

  getAllCompanies(): Observable<any> {
    return this.http.get(this.baseUrl + '/all', this.httpOptions);
  }

  getCompany(name: string): Observable<any> {
    return this.http.get(`${this.baseUrl}/find?q=${name}`, this.httpOptions);
  }
}
