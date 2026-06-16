import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../../material.module';
import { FinancialComponent } from './financial.component';

@NgModule({
  declarations: [
    FinancialComponent
  ],
  imports: [
    CommonModule,
    MaterialModule
  ]
})
export class FinancialModule { }
