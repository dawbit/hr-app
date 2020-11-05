import { QuizAssignment } from './../../../../../../classes/quizAssignment';
import { JobOffersService } from './../../../../../../services/job-offers.service';
import { Component, Input, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-quiz-assign-modal',
  templateUrl: './quiz-assign-modal.component.html',
  styleUrls: ['./quiz-assign-modal.component.scss']
})
export class QuizAssignModalComponent implements OnInit {
  @Input() announcementId: number;
  @Input() userId: number;

  jobOffersService: JobOffersService;
  quizAssignment: QuizAssignment;
  validatingForm: FormGroup;

  ngOnInit() {
    this.validatingForm = new FormGroup({
      loginFormModalEmail: new FormControl('', Validators.required),
      loginFormModalPassword: new FormControl('', Validators.required)
    });
  }

  get loginFormModalEmail() {
    return this.validatingForm.get('loginFormModalEmail');
  }

  get loginFormModalPassword() {
    return this.validatingForm.get('loginFormModalPassword');
  }

  assignQuiz(){
    // this.quizAssignment = {
    //   testName = this.validatingForm.get('loginFormModalPassword'),
    //   testCode = this.validatingForm.get('loginFormModalEmail'),
    //   testId = 1,
    //   userId = this.userId,
    //   read = true,
    //   announcementId = this.announcementId
    // };
    // this.jobOffersService.assignQuiz( ).subscribe(

    // );
  }

}
