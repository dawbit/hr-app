import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { User } from '../user';
import { UserService } from '../../../../../../services/user.service';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.scss']
})
export class UserDetailsComponent implements OnInit {

  id: number;
  user: User;

  constructor(private route: ActivatedRoute, private router: Router,
              private userService: UserService) { }

  ngOnInit() {
    this.user = new User();

    this.id = this.route.snapshot.params.id; // this.id = this.route.snapshot.params['id']; - krzyczało że nie można dobierać się stringiem

    this.userService.getUser(this.id)
      .subscribe(data => {
        this.user = data;
      });
  }

  list(){
    this.router.navigate(['admin-panel']);
  }
}
