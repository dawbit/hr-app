import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { QuizService } from './../../../services/quiz.service';
import { ToastService } from './../../../services/toast.service';

@Component({
  selector: 'app-quiz-solve',
  templateUrl: './quiz-solve.component.html',
  styleUrls: ['./quiz-solve.component.scss']
})
export class QuizSolveComponent implements OnInit {
  testCode: string;
  quizCodeFlag: boolean;
  currentQuestionNumber: number;
  currentQuestion: any;
  quizStarted: boolean;
  numberOfQuestion = 0;

  quizCodeForm: FormGroup;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private formBuilder: FormBuilder,
    private quizService: QuizService,
    private toast: ToastService
  ) { }

  ngOnInit(): void {
    // Przykładowe uruchomienie quizu poprzez posiadanie kodu
    // w przeciwnym wypadku, należy podać go ręcznie
    // http://localhost:4200/quiz-solve;testCode=10
    this.testCode = this.route.snapshot.params['testCode'];
    this.userHasQuizCode(this.testCode);

    this.quizCodeForm = this.formBuilder.group({
      testCode: ['', [Validators.required, Validators.minLength(1)]]
    });
  }

  userHasQuizCode(code: string) {
    if (!code || code.length <= 0) {
      this.quizCodeFlag = false;
    } else {
      this.quizCodeFlag = true;
    }
  }

  getQuizInfo() {
    let testCode;

    if (this.quizCodeFlag) {
      testCode = this.testCode;
    } else {
      testCode = this.quizCodeForm.value.testCode;
    }

    this.quizService.getQuizInfo(testCode).subscribe(
      res => {
        if (res && res.responseCode === 1) {
          // TODO
          this.toast.showSuccess('quiz.exists');
          // tutaj będzie wyskakujący pop-up
          // (który dodaje w innym miejscu Daniel - to tylko przekopiujemy)
          // który będzie pytał czy aby na pewno chcesz rozwiązać quiz ;)
          this.currentQuestionNumber = 1;
          this.numberOfQuestion = res.amountOfQuestions;
          this.startQuiz(res.quizId, testCode, this.currentQuestionNumber);
        }
      },
      err => {
        if (err && err.responseCode === 11) {
          this.toast.showError('quiz.notExists');
          // TODO do dodania obsługa informacji z innymi responseCode (dzisiaj już czasu nie miałem);
        } else {
          this.toast.showError('criticalError');
        }
      }
    );
  }

  // aktualnie nieużywany (zwraca całą zawartość - do obgadania
  // napewno będzie wykorzystany w apce)
  getQuizFullInfo(quizId: number) {
    this.quizService.getFullQuizInfo(quizId).subscribe(
      // TODO
    )
  }

  startQuiz(quizId: number, testCode: string, currentQuestionNumber) {
    this.quizStarted = true;
    this.quizService.getQuestion(quizId, testCode, currentQuestionNumber).subscribe(
      res => {
        this.currentQuestion = res;
        console.log(res);
        console.log(this.currentQuestion);
        if (res) {
        }
      },
      err => {
        // if (err && err.status === 409) {
        //   this.toast.showWarning('message.userAlreadyExists');
        // } else {
        //   this.toast.showError('message.notRegistered');
        // }
      }
    );
  }

}
