import { UserService } from './../../../services/user.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-user-panel',
  templateUrl: './user-panel.component.html',
  styleUrls: ['./user-panel.component.scss']
})
export class UserPanelComponent implements OnInit {
  selectedBookmark: string;
  applications: any = [];
  notifications = 0;

  constructor(
    private userService: UserService
  ) { }

  ngOnInit(): void {
    this.getAllUserApplications();
  }

  showBookmark(bookmark): string {
    return this.selectedBookmark = bookmark;
  }

  getAllUserApplications(){
    this.userService.getAllApplications().subscribe(res => {
      for (const key in res){
        if (res.hasOwnProperty(key)){
          this.applications.push({
            quizCode: res[key].quizCode,
            completed: res[key].completed
          });
        }
      }
      this.countNotifications();
    });
  }

  countNotifications(){
    this.applications.forEach(app => {
      if (app.quizCode && !app.completed){
        this.notifications++;
      }
    });
  }


}
