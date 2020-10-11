import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'transformFlag'
})
export class TransformFlagPipe implements PipeTransform {

  transform(lang: string): string {
    if (lang === 'en') {
      return 'gb';
    }
    else {
      return lang;
    }
  }

}
