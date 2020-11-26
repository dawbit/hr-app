import { TranslateService } from '@ngx-translate/core';
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

constructor(
  private http: HttpClient,
  private translate: TranslateService
  ) { }

  // Config for sending cv
getAfuConfig(){
  const afuConfig = {
    formatsAllowed: '.pdf',
    maxSize: '5',
    theme: 'dragNDrop',

    uploadAPI: {
      url: this.baseUrl + '/uploadCv',
      headers: {
        'content-Type': 'multipart/form-data',
        boundary: 'file'
      }
    },
    replaceTexts: {
      selectFileBtn: this.translate.instant('cvs.selectFileBtn'),
      resetBtn: this.translate.instant('cvs.resetBtn'),
      uploadBtn: this.translate.instant('cvs.uploadBtn'),
      dragNDropBox: this.translate.instant('cvs.dragNDropBox'),
      attachPinBtn: this.translate.instant('cvs.attachPinBtn'),
      afterUploadMsg_success: this.translate.instant('cvs.afterUploadMsg_success'),
      afterUploadMsg_error: this.translate.instant('cvs.afterUploadMsg_error'),
      sizeLimit: this.translate.instant('cvs.sizeLimit')
    }
  };

  return afuConfig;
}

getCv(){
  return this.http.get(`${this.baseUrl}/`);
}

}
