import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-account-settings',
  templateUrl: './account-settings.component.html',
  styleUrls: ['./account-settings.component.scss']
})
export class AccountSettingsComponent implements OnInit {

  constructor(
    private route: ActivatedRoute
  ) { }

  selectedBookmark = '';

  ngOnInit() {
    if (this.route.snapshot.params.card === 'edit-subscription') {
      this.selectedBookmark = 'my-account.mailSubscription';
    }
  }

  showBookmark(bookmark): string {
    return this.selectedBookmark = bookmark;
  }

}
