import { Company } from './company';
import { Component, Input, OnInit, ElementRef, AfterViewInit } from '@angular/core';

@Component({
  selector: 'app-company-list-single-element',
  templateUrl: './company-list-single-element.component.html',
  styleUrls: ['./company-list-single-element.component.scss']
})
export class CompanyListSingleElementComponent implements OnInit {

   @Input() text: string;
   @Input() maxLength = 100;

   public isCollapsed = false;
   public isCollapsable = false;

   constructor(private elementRef: ElementRef) {
   }

   ngOnInit() {
       const textLength = this.text.length;
      // collapsuje tylko, jeśli tekst z inputu przekroczy ustaloną długość
       if (textLength > this.maxLength) {
           this.isCollapsed = true;
           this.isCollapsable = true;
       }
   }

}
