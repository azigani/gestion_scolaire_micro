import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../../material.module';
import { AcademicComponent } from './academic.component';
import { CourseDialogComponent } from './course-dialog.component';

@NgModule({
  declarations: [
    AcademicComponent,
    CourseDialogComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    MaterialModule
  ]
})
export class AcademicModule { }
