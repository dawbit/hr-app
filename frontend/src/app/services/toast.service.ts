import { Injectable } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { TranslateService } from '@ngx-translate/core';

@Injectable({
  providedIn: 'root'
})
export class ToastService {

  constructor(
    private toastr: ToastrService,
    private translate: TranslateService
  ) { }

  showSuccess(major) {
    this.toastr.success(this.translate.instant(major), this.translate.instant('toast.success'), {
      timeOut: 3000
    });
  }

  showError(major) {
    this.toastr.error(this.translate.instant(major), this.translate.instant('toast.error'), {
      timeOut: 3000
    });
  }

  showWarning(major) {
    this.toastr.warning(this.translate.instant(major), this.translate.instant('toast.warning'), {
      timeOut: 3000
    });
  }

  showInfo(major) {
    this.toastr.info(this.translate.instant(major), this.translate.instant('toast.info'), {
      timeOut: 3000
    });
  }

}
