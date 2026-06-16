import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../../material.module';
import { DashboardComponent } from './dashboard.component';

@NgModule({
  declarations: [
    DashboardComponent
  ],
  imports: [
    CommonModule,
    MaterialModule
  ]
})
export class DashboardModule { }
