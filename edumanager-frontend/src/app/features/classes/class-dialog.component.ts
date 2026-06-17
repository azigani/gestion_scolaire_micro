import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ApiService } from '../../core/services/api.service';

@Component({
  selector: 'app-class-dialog',
  template: `
    <h2 mat-dialog-title>{{ data.id ? 'Edit Class' : 'Add New Class' }}</h2>
    <mat-dialog-content>
      <form [formGroup]="classForm">
        <mat-form-field appearance="fill" class="full-width">
          <mat-label>Class Name</mat-label>
          <input matInput formControlName="name" required>
          <mat-error *ngIf="classForm.get('name')?.hasError('required')">
            Class name is required
          </mat-error>
        </mat-form-field>

        <mat-form-field appearance="fill" class="full-width">
          <mat-label>Grade Level</mat-label>
          <mat-select formControlName="grade" required>
            <mat-option value="1">Grade 1</mat-option>
            <mat-option value="2">Grade 2</mat-option>
            <mat-option value="3">Grade 3</mat-option>
            <mat-option value="4">Grade 4</mat-option>
            <mat-option value="5">Grade 5</mat-option>
            <mat-option value="6">Grade 6</mat-option>
            <mat-option value="7">Grade 7</mat-option>
            <mat-option value="8">Grade 8</mat-option>
            <mat-option value="9">Grade 9</mat-option>
            <mat-option value="10">Grade 10</mat-option>
            <mat-option value="11">Grade 11</mat-option>
            <mat-option value="12">Grade 12</mat-option>
          </mat-select>
        </mat-form-field>

        <mat-form-field appearance="fill" class="full-width">
          <mat-label>Teacher</mat-label>
          <input matInput formControlName="teacher" required>
          <mat-error *ngIf="classForm.get('teacher')?.hasError('required')">
            Teacher is required
          </mat-error>
        </mat-form-field>

        <mat-form-field appearance="fill" class="full-width">
          <mat-label>Capacity</mat-label>
          <input matInput type="number" formControlName="capacity" required min="1">
          <mat-error *ngIf="classForm.get('capacity')?.hasError('required')">
            Capacity is required
          </mat-error>
        </mat-form-field>

        <mat-form-field appearance="fill" class="full-width">
          <mat-label>Room Number</mat-label>
          <input matInput formControlName="roomNumber">
        </mat-form-field>

        <mat-form-field appearance="fill" class="full-width">
          <mat-label>Status</mat-label>
          <mat-select formControlName="status">
            <mat-option value="ACTIVE">Active</mat-option>
            <mat-option value="INACTIVE">Inactive</mat-option>
            <mat-option value="ARCHIVED">Archived</mat-option>
          </mat-select>
        </mat-form-field>
      </form>
    </mat-dialog-content>
    <mat-dialog-actions align="end">
      <button mat-button (click)="onCancel()">Cancel</button>
      <button mat-raised-button color="primary" (click)="onSave()" [disabled]="classForm.invalid">
        Save
      </button>
    </mat-dialog-actions>
  `,
  styles: [`
    .full-width {
      width: 100%;
      margin-bottom: 15px;
    }
  `]
})
export class ClassDialogComponent {
  classForm: FormGroup;

  constructor(
    private dialogRef: MatDialogRef<ClassDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private fb: FormBuilder,
    private apiService: ApiService
  ) {
    this.classForm = this.fb.group({
      name: [data.name || '', Validators.required],
      grade: [data.grade || '', Validators.required],
      teacher: [data.teacher || '', Validators.required],
      capacity: [data.capacity || 30, [Validators.required, Validators.min(1)]],
      roomNumber: [data.roomNumber || ''],
      status: [data.status || 'ACTIVE']
    });
  }

  onSave(): void {
    if (this.classForm.valid) {
      const classData = this.classForm.value;
      
      if (this.data.id) {
        this.apiService.put(`/classes/${this.data.id}`, classData).subscribe({
          next: () => this.dialogRef.close(true),
          error: (error) => console.error('Error updating class:', error)
        });
      } else {
        this.apiService.post('/classes', classData).subscribe({
          next: () => this.dialogRef.close(true),
          error: (error) => console.error('Error creating class:', error)
        });
      }
    }
  }

  onCancel(): void {
    this.dialogRef.close();
  }
}
