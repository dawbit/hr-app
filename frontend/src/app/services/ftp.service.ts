import { Injectable } from '@angular/core';
import { GlobalConstants } from '../common/global-constants';

@Injectable({
  providedIn: 'root'
})
export class FtpService {
  private apiURL = GlobalConstants.apiURL;
  private baseUrl = GlobalConstants.apiURL + '/companies';
  private httpOptions = GlobalConstants.httpOptions;

constructor() { }



}
