import { Component } from '@angular/core';
// import { RouterModule } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  title = 'HR app';

  constructor(
    public translate: TranslateService
  ) {
    translate.addLangs(['en', 'pl']);
    translate.setDefaultLang('en');
  }
}
