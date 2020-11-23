import { Component, HostListener, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MailingService } from './../../../../services/mailing.service';
import { ToastService } from './../../../../services/toast.service';

@Component({
  selector: 'app-contact-email',
  templateUrl: './contact-email.component.html',
  styleUrls: ['./contact-email.component.scss']
})
export class ContactEmailComponent implements OnInit {

  contactForm: FormGroup;
  disabledSubmitButton = true;
  optionsSelect: Array<any>;

  @HostListener('input') oninput() {

    if (this.contactForm.valid) {
      this.disabledSubmitButton = false;
    }
  }

  constructor(
    public fb: FormBuilder,
    private mailingService: MailingService,
    private toast: ToastService
  ) {

    this.contactForm = fb.group({
      subject: ['', Validators.required],
      authorMail: ['', Validators.compose([Validators.required, Validators.email])],
      textMessage: ['', Validators.required]
    });
  }

  ngOnInit() {

  }

  get subject() {
    return this.contactForm.get('subject');
  }
  get textMessage() {
    return this.contactForm.get('textMessage');
  }
  get authorMail() {
    return this.contactForm.get('authorMail');
  }

  onSubmit() {
    this.mailingService.sendMessage(this.contactForm.value).subscribe(() => {
      this.toast.showSuccess('contact.mailSent');
    }, (error: any) => {
      this.toast.showError('contact.mailNotSent');
    });
  }

}
