import { Component, Input, OnInit } from '@angular/core';
import { JobOffersService } from 'src/app/services/job-offers.service';

@Component({
  selector: 'app-quiz-result-modal',
  templateUrl: './quiz-result-modal.component.html',
  styleUrls: ['./quiz-result-modal.component.scss']
})
export class QuizResultModalComponent implements OnInit {
  @Input() userId: number;
  @Input() quizId: number;

  quizname: string;
  quizResult: number;
  quizMaxPoints: number;
  userLogin: string;
  userQuestions: any = [];

  constructor(
    private jobOffersService: JobOffersService
  ) { }

  ngOnInit() {

  }

  getResults(userId, quizId){
    this.jobOffersService.getResult(userId, quizId).subscribe(res => {
      console.log(res);
      this.quizname = res.quizName,
      this.quizResult = res.userPoints,
      this.quizMaxPoints = res.quizMaxPoints,
      this.userLogin = res.login

      res.userQuestionResultDtoList.forEach(el => {
        this.userQuestions.push({
          question: el.questionText,
          answer: el.answerText,
          maxPoints: el.maxPoints,
          userPoints: el.userPoints
        })
      });
    })
  }

  getInfo(){
    console.log("xxx");
    console.log(this.userId);
    this.getResults(this.userId, this.quizId);
  }

}
