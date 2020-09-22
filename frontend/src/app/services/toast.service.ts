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

  showSuccess(minor) {
    this.toastr.success(this.translate.instant('toast.success'), this.translate.instant(minor), {
      timeOut: 2000
    });
  }

  showError(minor) {
    this.toastr.error(this.translate.instant('toast.error'), this.translate.instant(minor), {
      timeOut: 2000
    });
  }

  showWarning(minor) {
    this.toastr.warning(this.translate.instant('toast.warning'), this.translate.instant(minor), {
      timeOut: 2000
    });
  }

  showInfo(minor) {
    this.toastr.info(this.translate.instant('toast.info'), this.translate.instant(minor), {
      timeOut: 2000
    });
  }

}
