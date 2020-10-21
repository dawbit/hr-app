import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormArray, FormControl } from '@angular/forms';

@Component({
  selector: 'app-quiz-create-panel',
  templateUrl: './quiz-create-panel.component.html',
  styleUrls: ['./quiz-create-panel.component.scss']
})
export class QuizCreatePanelComponent implements OnInit {

  userForm: FormGroup;
  answersModelfields: any;
  constructor(
    private fb: FormBuilder
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
    // definicja obiektu - formularza odpowiedzialnego za questionsJsonModel
    this.userForm = new FormGroup({
      questionsJsonModel: new FormArray([
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

  loadValues() {
    this.userForm.patchValue({
      questionsJsonModel: [
        {
          questionsModel: {
            text: 'rabarbarowelowe'
          },
          answersModel: [
            {
              text: 'aa',
              is_correct: 'bb',
              points: 'cc'
            }
          ]
        }
      ]
    });
  }

  addAnswer(ind) {
    // tslint:disable-next-line: no-string-literal
    const control = this.userForm.get('questionsJsonModel')['controls'][ind]['controls']['answersModel'] as FormArray;
    this.answersModelfields.type.options.forEach(x => {
      control.push(this.patchValues(x.is_correct, x.points, x.text));
    });

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
    (this.userForm.get('questionsJsonModel') as FormArray).push(
      this.fb.group(
        {
          questionsModel: this.fb.group({
            text: ''
          }),
          answersModel: this.fb.array([])
        }
      )
    );
    this.addAnswer(ind);
  }

  removeAnswer(ind, index) {
    // tslint:disable-next-line: no-string-literal
    (this.userForm.get('questionsJsonModel')['controls'][ind]['controls']['answersModel'] as FormArray).removeAt(index);
  }

  removeQuestion(ind) {
    (this.userForm.get('questionsJsonModel') as FormArray).removeAt(ind);
  }

  send(values) {
    console.log(values);
  }
}
