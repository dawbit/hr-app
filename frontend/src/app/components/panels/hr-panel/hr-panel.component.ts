import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-hr-panel',
  templateUrl: './hr-panel.component.html',
  styleUrls: ['./hr-panel.component.scss']
})
export class HrPanelComponent implements OnInit {

  selectedBookmark: string;
  constructor() { }

  ngOnInit(): void {
  }

  showBookmark(bookmark): string {
    return this.selectedBookmark = bookmark;
  }


}
