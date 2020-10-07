import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TransformFlagPipe } from '../pipes/transform-flag.pipe';


@NgModule({
  declarations: [TransformFlagPipe],
  imports: [
    CommonModule
  ],
  exports: [
    TransformFlagPipe
  ]
})
export class PipesModule { }
