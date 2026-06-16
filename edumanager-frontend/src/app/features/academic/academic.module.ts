import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../../material.module';
import { AcademicComponent } from './academic.component';

@NgModule({
  declarations: [
    AcademicComponent
  ],
  imports: [
    CommonModule,
    MaterialModule
  ]
})
export class AcademicModule { }
