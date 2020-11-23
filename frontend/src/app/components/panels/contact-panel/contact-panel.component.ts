import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-contact-panel',
  templateUrl: './contact-panel.component.html',
  styleUrls: ['./contact-panel.component.scss']
})
export class ContactPanelComponent implements OnInit {

  constructor() { }

  selectedBookmark = '';

  ngOnInit() {
  }

  showBookmark(bookmark): string {
    return this.selectedBookmark = bookmark;
  }

}
