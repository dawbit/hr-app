import { AfterViewInit, Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormArray, AbstractControl, FormControl } from '@angular/forms';

@Component({
  selector: 'app-quiz-create-panel',
  templateUrl: './quiz-create-panel.component.html',
  styleUrls: ['./quiz-create-panel.component.scss']
})
export class QuizCreatePanelComponent implements OnInit, AfterViewInit {

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



    // this.userForm = this.fb.group({
    //   questionsModel: this.fb.group({
    //     text: ''
    //   }),
    //   answersModel: this.fb.array([])
    // });





    //console.log(this.userForm.get('questionsJsonModel')['controls'][0]);
    //console.log(this.userForm.get('answersModel')['controls']);
    // console.log(this.userForm.get('questionsJsonModel')['controls']);
    // console.log(this.userForm.get('questionsJsonModel')['controls'][0]['controls']['answersModel']['controls']);
    // console.log(this.userForm.value.questionsJsonModel);


    //this.patch();
  }

  ngOnInit(): void {
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

    // this.userForm.setValue({
    //   questionsJsonModel: [
    //     {
    //       questionsModel: {
    //         text: 'rabarbarowelowe'
    //       },
    //       answersModel: [
    //         {
    //           text: "",
    //           is_correct: "",
    //           points: ""
    //         }
    //       ]
    //     }
    //   ]
    // });

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
              text: "",
              is_correct: "",
              points: ""
            }
          ]
        }
      ]
    });
  }

  ngAfterViewInit(): void {
    // this.userForm.setValue({
    //   questionsJsonModel: [
    //     {
    //       questionsModel: {
    //         text: 'rabarbarowelowe'
    //       },
    //       answersModel: [
    //         {
    //           text: "",
    //           is_correct: "",
    //           points: ""
    //         }
    //       ]
    //     }
    //   ]
    // });
  }

  patch(ind) {
    // console.log(ind);
    // console.log(this.userForm.get('questionsJsonModel')['controls'][ind]['controls']['answersModel']['controls']);

    //<FormArray>this.userForm.get('questionsJsonModel')['controls'][ind]['controls']['answersModel']
    //const control = <FormArray>this.userForm.get('answersModel');

    const control = <FormArray>this.userForm.get('questionsJsonModel')['controls'][ind]['controls']['answersModel'];

    //console.log(control);

    this.fields.type.options.forEach(x => {
      control.push(this.patchValues(x.is_correct, x.points, x.text));
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

  newQuest(ind) {
    console.log(ind);
    ind = Object.keys(ind).length;
    console.log(ind);
    console.log(this.userForm.get('questionsJsonModel')['controls']);
    //console.log(this.userForm.get('questionsJsonModel')['controls']);

    //   questionsModel: this.fb.group({
    //     text: ''
    //   }),
    //   answersModel: this.fb.array([])


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

    this.patch(ind);

    console.log(this.userForm.get('questionsJsonModel')['controls']);
    // console.log(this.userForm.get('questionsJsonModel')['controls']);
    // console.log(this.userForm.get('questionsJsonModel')['controls'][0]);
    // console.log(this.userForm.get('questionsJsonModel')['controls'][1]);
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

  removePhone(ind, index) {
    (this.userForm.get('questionsJsonModel')['controls'][ind]['controls']['answersModel'] as FormArray).removeAt(index);
    //(this.userForm.get('answersModel') as FormArray).removeAt(index);
  }

  getPhonesFormControls(): AbstractControl[] {
    //console.log((<FormArray>this.userForm.get('answersModel')).controls);
    return (<FormArray>this.userForm.get('answersModel')).controls;
  }

  send(values) {
    //console.log(values);
  }
}
