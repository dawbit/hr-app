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
            is_correct: '',
            points: '',
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
              is_correct: new FormControl(''),
              points: new FormControl(''),
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
    const control = <FormArray>this.userForm.get('questionsJsonModel')['controls'][ind]['controls']['answersModel'];
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
    (this.userForm.get('questionsJsonModel')['controls'][ind]['controls']['answersModel'] as FormArray).removeAt(index);
  }

  removeQuestion(ind) {
    (this.userForm.get('questionsJsonModel') as FormArray).removeAt(ind);
  }

  send(values) {
    console.log(values);
  }
}
