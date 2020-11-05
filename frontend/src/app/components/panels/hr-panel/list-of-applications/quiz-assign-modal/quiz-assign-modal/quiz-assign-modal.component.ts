import { QuizAssignment } from './../../../../../../classes/quizAssignment';
import { QuizService } from './../../../../../../services/quiz.service';
import { JobOffersService } from './../../../../../../services/job-offers.service';
import { Component, Input, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import { ToastService } from '../../../../../../services/toast.service';

@Component({
  selector: 'app-quiz-assign-modal',
  templateUrl: './quiz-assign-modal.component.html',
  styleUrls: ['./quiz-assign-modal.component.scss']
})
export class QuizAssignModalComponent implements OnInit {
  @Input() announcementId: number;
  @Input() alertId: number;
  @Input() userId: number;

  validatingForm: FormGroup;
  quizAssignment: QuizAssignment;
  public quizList: any = [];

  constructor(
    private jobOffersService: JobOffersService,
    private quizService: QuizService,
    private toast: ToastService
  ){}

  ngOnInit() {
    this.listOfQuiz();

    this.validatingForm = new FormGroup({
      testName: new FormControl('', Validators.required),
      testCode: new FormControl('', Validators.required),
    });
  }

  get testCode() {
    return this.validatingForm.get('testCode');
  }

  get testName() {
    return this.validatingForm.get('testName');
  }

  listOfQuiz(){
    this.quizService.getQuizList().subscribe(
      res => {
        for (const key in res){
          if (res.hasOwnProperty(key)){
            this.quizList.push({
              responseCode: res[key].responseCode,
              quizId: res[key].quizId,
              amountOfQuestions: res[key].amountOfQuestions,
              timeForTestInMilis: res[key].timeForTestInMilis,
              quizName: res[key].quizName,
              companyId: res[key].companyId,
              userHrId: res[key].userHrId,
              backPossible: res[key].backPossible
            });
          }
        }
      }
    );
  }

  assignQuiz(){
    this.quizAssignment = {
      testName: this.validatingForm.get('testName').value.quizName,
      testCode: this.validatingForm.get('testCode').value,
      testId: this.validatingForm.get('testName').value.quizId,
      userId: this.userId,
      read: true,
      announcementId: this.announcementId
    };

    console.log(this.quizAssignment);

    this.jobOffersService.assignQuiz(this.alertId, this.quizAssignment).subscribe(
      res => {
        if (res && res.ok && res.status === 200) {
          this.toast.showSuccess('message.quizAssigned');
        }
      },
      err => {
        this.toast.showError('message.quizNotAssighned');
      }
    );
  }

}
