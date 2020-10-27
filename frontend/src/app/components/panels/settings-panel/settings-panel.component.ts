import { Component, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-settings-panel',
  templateUrl: './settings-panel.component.html',
  styleUrls: ['./settings-panel.component.scss']
})
export class SettingsPanelComponent implements OnInit {

  selectedBookmark = 'language';
  languages = [];
  selectedLanguage: string;
  languageFlag: string;

  constructor(
    private translate: TranslateService
  ) { }

  ngOnInit(): void {
    this.languages = this.translate.getLangs();
    if (localStorage.getItem('lang')) {
      this.selectedLanguage = localStorage.getItem('lang');
    } else {
      this.selectedLanguage = this.translate.getDefaultLang();
      localStorage.setItem('lang', this.selectedLanguage);
    }
  }

  showBookmark(bookmark): string {
    return this.selectedBookmark = bookmark;
  }

  switchLanguage() {
    this.translate.use(this.selectedLanguage);
    localStorage.setItem('lang', this.selectedLanguage);
  }

}
