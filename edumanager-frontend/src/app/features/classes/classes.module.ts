import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../../material.module';
import { ClassesComponent } from './classes.component';
import { ClassDialogComponent } from './class-dialog.component';

@NgModule({
  declarations: [
    ClassesComponent,
    ClassDialogComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    MaterialModule
  ]
})
export class ClassesModule { }
