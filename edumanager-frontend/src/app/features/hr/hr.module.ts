import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../../material.module';
import { HrComponent } from './hr.component';

@NgModule({
  declarations: [
    HrComponent
  ],
  imports: [
    CommonModule,
    MaterialModule
  ]
})
export class HrModule { }
