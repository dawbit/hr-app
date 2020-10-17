import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormArray, AbstractControl, FormControl } from '@angular/forms';

@Component({
  selector: 'app-quiz-create-panel',
  templateUrl: './quiz-create-panel.component.html',
  styleUrls: ['./quiz-create-panel.component.scss']
})
export class QuizCreatePanelComponent implements OnInit {

  userForm: FormGroup;
  fields: any;
  constructor(
    private fb: FormBuilder
  ) {
    this.fields = {
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



    this.userForm = this.fb.group({
      questionsModel: this.fb.group({
        text: ''
      }),
      answersModel: this.fb.array([])
    });

    // this.userForm = new FormGroup({
    //   questionsJsonModel: new FormArray([
    //     new FormGroup({
    //       questionsModel: new FormArray([
    //         new FormGroup({
    //           text: new FormControl(''),
    //         })
    //       ]),
    //       answersModel: new FormArray([
    //         new FormGroup({
    //           text: new FormControl(''),
    //           is_correct: new FormControl(''),
    //           points: new FormControl(''),
    //         })
    //       ]),
    //     })
    //   ])
    // });

    //this.patch();
  }

  ngOnInit(): void {

  }

  patch() {
    const control = <FormArray>this.userForm.get('answersModel');
    this.fields.type.options.forEach(x => {
      control.push(this.patchValues(x.is_correct, x.points, x.text))
    });
  }

  patchValues(is_correct, points, text) {
    return this.fb.group({
      is_correct: [is_correct],
      points: [points],
      text: [text]
    });
  }

  get answersModel(): FormArray {
    return this.userForm.get('answersModel') as FormArray;
  }

  // addPhone(): void {
  //   (this.userForm.get('answersModel') as FormArray).push(
  //     this.fb.control({
  //       is_correct: '',
  //       points: '',
  //       text: ''
  //     })
  //   );
  // }

  removePhone(index) {
    (this.userForm.get('answersModel') as FormArray).removeAt(index);
  }

  getPhonesFormControls(): AbstractControl[] {
    console.log((<FormArray>this.userForm.get('answersModel')).controls);
    return (<FormArray>this.userForm.get('answersModel')).controls;
  }

  send(values) {
    console.log(values);
  }
}
