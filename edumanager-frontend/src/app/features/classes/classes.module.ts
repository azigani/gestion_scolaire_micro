import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../../material.module';
import { ClassesComponent } from './classes.component';

@NgModule({
  declarations: [
    ClassesComponent
  ],
  imports: [
    CommonModule,
    MaterialModule
  ]
})
export class ClassesModule { }
