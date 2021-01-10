import { TranslateService } from '@ngx-translate/core';
import { HttpClient, HttpBackend } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { GlobalConstants } from '../common/global-constants';
import { TokenStorageService } from './security/token-storage.service';

@Injectable({
  providedIn: 'root'
})
export class FtpService {
  private apiURL = GlobalConstants.apiURL;
  private baseUrl = GlobalConstants.apiURL + '/cvs';
  private http: HttpClient;
constructor(
  private translate: TranslateService,
  private handler: HttpBackend,
  private auth: TokenStorageService
  ) { this.http = new HttpClient(handler) }

  // Config for sending cv
getAfuConfig(){
  const afuConfig = {
    formatsAllowed: '.pdf',
    theme: 'dragNDrop',
    multiple: false,
    fileNameIndex: false,
    uploadAPI: {
      url: this.baseUrl + '/uploadCv',
      headers: {
        Authorization: this.auth.getToken(),
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
