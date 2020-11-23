import { Component, OnInit, ChangeDetectorRef, AfterViewInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MailingService } from './../../../../services/mailing.service';
import { ToastService } from './../../../../services/toast.service';

@Component({
  selector: 'app-mail-subscription',
  templateUrl: './mail-subscription.component.html',
  styleUrls: ['./mail-subscription.component.scss']
})
export class MailSubscriptionComponent implements OnInit, AfterViewInit {

  constructor(
    private mailingService: MailingService,
    private formBuilder: FormBuilder,
    private cdRef: ChangeDetectorRef,
    private toast: ToastService
  ) { }

  mailingId: number;
  isResponse = false;
  public mailingForm: FormGroup;

  ngOnInit() {
    this.getMailingList();
    this.mailingForm = this.formBuilder.group({
      mailingNewQuiz: [Validators.required]
    });
  }

  ngAfterViewInit() {
    this.cdRef.detectChanges();
  }

  getMailingList() {
    this.mailingService.mailingList().subscribe(res => {
      console.log(res)
      if (res.id && res.mailingNewQuiz.toString().length) {
        this.mailingId = res.id;
        this.mailingForm.controls.mailingNewQuiz.setValue(res.mailingNewQuiz);
        this.isResponse = true;
      } else {
        this.toast.showError('toast.error');
      }
    }, err => {
      this.toast.showError('toast.error');
    });
    return;
  }

  onSubmit(value) {
    // w przyszłości, jeżeli będzie więcej opcji, będzie wysyłany
    // mailingId, oraz lista subskrypcji
    this.mailingService.saveMailingList(this.mailingId, value.mailingNewQuiz).subscribe(res => {
      this.toast.showSuccess('my-account.mailSubscriptionChanged');
    }, err => {
      this.toast.showError('toast.error');
    });
  }

}
