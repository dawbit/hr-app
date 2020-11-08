import { AfterViewInit, Component, HostListener, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormArray, FormControl } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { CryptoService } from './../../../services/security/crypto.service';
import Swal from 'sweetalert2/dist/sweetalert2.js';
import { Router } from '@angular/router';
import { QuizService } from './../../../services/quiz.service';
import { ToastService } from './../../../services/toast.service';

@Component({
  selector: 'app-quiz-create-panel',
  templateUrl: './quiz-create-panel.component.html',
  styleUrls: ['./quiz-create-panel.component.scss']
})
export class QuizCreatePanelComponent implements OnInit, AfterViewInit {

  questionsModel: FormGroup;
  testsModel: FormGroup;
  answersModelfields: any;
  changes = false;

  @HostListener('window:beforeunload', ['$event'])
  doSomething($event) {
    if (this.changes) {
      const quizData = this.cryptoService.encryptData({ quizInfo: this.testsModel.value, quizValues: this.questionsModel.value });
      localStorage.setItem('quiz-create-panel-data', (quizData).toString());
      $event.returnValue = this.translate.instant('closeTab');
    }
  }

  constructor(
    private fb: FormBuilder,
    private translate: TranslateService,
    private cryptoService: CryptoService,
    private router: Router,
    private quizService: QuizService,
    private toast: ToastService
  ) {
    // wstrzykiwanie wartości kluczy z pustymi wartościami do FormArray
    this.answersModelfields = {
      isRequired: true,
      type: {
        options: [
          {
            is_correct: false,
            points: 0,
            text: ''
          }
        ]
      }
    };
  }

  ngOnInit(): void {
    this.testsModel = new FormGroup({
      is_possible_to_back: new FormControl(true),
      name: new FormControl(''),
      timeForTestInMilis: new FormControl(1000)
    });

    // definicja obiektu - formularza odpowiedzialnego za listOfQuestionCommandDto
    this.questionsModel = new FormGroup({
      listOfQuestionCommandDto: new FormArray([
        new FormGroup({
          questionsModel: new FormGroup({
            text: new FormControl(''),
          }),
          answersModel: new FormArray([
            new FormGroup({
              text: new FormControl(''),
              is_correct: new FormControl(false),
              points: new FormControl(0),
            })
          ]),
        })
      ])
    });
  }

  ngAfterViewInit() {
    if (localStorage.getItem('quiz-create-panel-data')) {
      Swal.fire({
        title: this.translate.instant('quiz.loadConfirmation'),
        text: this.translate.instant('quiz.loadConfirmation2'),
        icon: 'question',
        showCancelButton: true,
        confirmButtonText: this.translate.instant('yes'),
        cancelButtonText: this.translate.instant('no')
      }).then((loadQuiz) => {
        if (loadQuiz.value) {
          Swal.fire({
            title: this.translate.instant('quiz.loaded'),
            icon: 'success',
          });
          const quizStructure = this.cryptoService.decryptData(localStorage.getItem('quiz-create-panel-data'));
          this.loadValues(quizStructure.quizInfo, quizStructure.quizValues);
        } else if (loadQuiz.dismiss === Swal.DismissReason.cancel) {
          Swal.fire({
            title: this.translate.instant('quiz.newQuiz'),
            icon: 'warning'
          });
          localStorage.removeItem('quiz-create-panel-data');
        }
      });
    }
  }

  loadValues(quizInfo, quizValues) {
    this.testsModel.setValue(quizInfo);
    this.questionsModel.patchValue(quizValues);
  }

  addAnswer(ind) {
    // tslint:disable-next-line: no-string-literal
    const control = this.questionsModel.get('listOfQuestionCommandDto')['controls'][ind]['controls']['answersModel'] as FormArray;
    this.answersModelfields.type.options.forEach(x => {
      control.push(this.patchValues(x.is_correct, x.points, x.text));
    });
    this.isChanged();
  }

  patchValues(isCorrect, points, text) {
    return this.fb.group({
      is_correct: [isCorrect],
      points: [points],
      text: [text]
    });
  }

  newQuestion(ind) {
    ind = Object.keys(ind).length;
    (this.questionsModel.get('listOfQuestionCommandDto') as FormArray).push(
      this.fb.group(
        {
          questionsModel: this.fb.group({
            text: ''
          }),
          answersModel: this.fb.array([])
        }
      )
    );
    this.isChanged();
    this.addAnswer(ind);
  }

  removeAnswer(ind, index) {
    // tslint:disable-next-line: no-string-literal
    (this.questionsModel.get('listOfQuestionCommandDto')['controls'][ind]['controls']['answersModel'] as FormArray).removeAt(index);
    this.isChanged();
  }

  removeQuestion(ind) {
    (this.questionsModel.get('listOfQuestionCommandDto') as FormArray).removeAt(ind);
    this.isChanged();
  }

  isChanged() {
    this.changes = true;
  }

  send() {
    const preparedTestModel = {
      isPossibleToBack: this.testsModel.value.is_possible_to_back,
      name: this.testsModel.value.name,
      timeForTestInMilis: (Number(this.testsModel.value.timeForTestInMilis) * 1000),
      };

    const body = Object.assign({ testsModel: preparedTestModel}, this.questionsModel.value);
    // const body = Object.assign({ testsModel: this.testsModel.value }, this.questionsModel.value);
    this.quizService.sendQuiz(body).subscribe(
      res => {
        if (res && res.status && res.ok) {
          this.toast.showSuccess('quiz.added');
          localStorage.removeItem('quiz-create-panel-data');
        }
      },
      err => {
        this.toast.showError('quiz.error');
      }
    );
  }
}
