import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../../material.module';
import { StudentsComponent } from './students.component';

@NgModule({
  declarations: [
    StudentsComponent
  ],
  imports: [
    CommonModule,
    MaterialModule
  ]
})
export class StudentsModule { }
