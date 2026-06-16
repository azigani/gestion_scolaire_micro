import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../../material.module';
import { CommunicationComponent } from './communication.component';

@NgModule({
  declarations: [
    CommunicationComponent
  ],
  imports: [
    CommonModule,
    MaterialModule
  ]
})
export class CommunicationModule { }
