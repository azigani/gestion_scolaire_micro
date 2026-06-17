import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../../material.module';
import { StudentsComponent } from './students.component';
import { StudentDialogComponent } from './student-dialog.component';

@NgModule({
  declarations: [
    StudentsComponent,
    StudentDialogComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    MaterialModule
  ]
})
export class StudentsModule { }
