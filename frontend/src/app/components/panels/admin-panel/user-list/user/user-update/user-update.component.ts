import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { User } from '../user';
import { UserService } from '../../../../../../services/user.service';

@Component({
  selector: 'app-user-update',
  templateUrl: './user-update.component.html',
  styleUrls: ['./user-update.component.scss']
})
export class UserUpdateComponent implements OnInit {
  form: FormGroup;
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService,
    private formBuilder: FormBuilder
    ) { }

  id: number;
  user: User;
  submitted = false;
  error = false;

  ngOnInit() {

    this.form = this.formBuilder.group({
      // password: new FormControl('', [Validators.required, Validators.pattern('[a-zA-Z0-9$@$!%*?&].{6,}$')]),
      firstName: new FormControl('', [Validators.required, Validators.pattern('[a-zA-Z0-9 ].{0,20}$')]),
      surname: new FormControl('', [Validators.required, Validators.pattern('[a-zA-Z0-9 ].{0,20}$')]),
      email: ['', [Validators.required, Validators.email]],
      middleName: ['', [Validators.required]],
      phoneNumber: ['', [Validators.required, Validators.minLength(9),
        Validators.pattern(/^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\s\./0-9]*$/)]
        ],
    });

    this.user = new User();
    this.id = this.route.snapshot.params.id;

    this.userService.getUser(this.id)
      .subscribe(data => {
        this.user = data;
      });
  }

    // convenience getter for easy access to form fields
    get f() { return this.form.controls; }

  updateUser() {
    this.userService.updateUser(this.id, this.user).subscribe();
    this.user = new User();
  }

  validError = (controlName: string, errorName: string) => {
    return this.form.controls[controlName].hasError(errorName);
  }

  onSubmit() {
    console.log(this.form);
    if (this.form.invalid){
      this.error = true;
      return;
    }
    else{
      this.error = false;
      this.submitted = true;
      this.updateUser();
    }
  }
}
