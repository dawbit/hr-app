import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { GlobalConstants } from '../common/global-constants';

@Injectable({
  providedIn: 'root'
})
export class FtpService {
  private apiURL = GlobalConstants.apiURL;
  private baseUrl = GlobalConstants.apiURL + '/cvs';
  private httpOptions = GlobalConstants.httpOptions;

constructor(private http: HttpClient) { }

addCv(cv: FormData){
  return this.http.post(`${this.baseUrl}/uploadCv`, cv);
}

getCv(){
  return this.http.get(`${this.baseUrl}/`);
}

}
