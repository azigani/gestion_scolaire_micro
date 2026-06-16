import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../../material.module';
import { SettingsComponent } from './settings.component';

@NgModule({
  declarations: [
    SettingsComponent
  ],
  imports: [
    CommonModule,
    MaterialModule
  ]
})
export class SettingsModule { }
