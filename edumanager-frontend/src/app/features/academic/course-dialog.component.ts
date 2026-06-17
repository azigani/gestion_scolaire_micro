import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ApiService } from '../../core/services/api.service';

@Component({
  selector: 'app-course-dialog',
  template: `
    <h2 mat-dialog-title>{{ data.id ? 'Edit Course' : 'Add New Course' }}</h2>
    <mat-dialog-content>
      <form [formGroup]="courseForm">
        <mat-form-field appearance="fill" class="full-width">
          <mat-label>Course Name</mat-label>
          <input matInput formControlName="name" required>
          <mat-error *ngIf="courseForm.get('name')?.hasError('required')">
            Course name is required
          </mat-error>
        </mat-form-field>

        <mat-form-field appearance="fill" class="full-width">
          <mat-label>Course Code</mat-label>
          <input matInput formControlName="code" required>
          <mat-error *ngIf="courseForm.get('code')?.hasError('required')">
            Course code is required
          </mat-error>
        </mat-form-field>

        <mat-form-field appearance="fill" class="full-width">
          <mat-label>Credits</mat-label>
          <input matInput type="number" formControlName="credits" required min="1">
          <mat-error *ngIf="courseForm.get('credits')?.hasError('required')">
            Credits are required
          </mat-error>
        </mat-form-field>

        <mat-form-field appearance="fill" class="full-width">
          <mat-label>Teacher</mat-label>
          <input matInput formControlName="teacher" required>
          <mat-error *ngIf="courseForm.get('teacher')?.hasError('required')">
            Teacher is required
          </mat-error>
        </mat-form-field>

        <mat-form-field appearance="fill" class="full-width">
          <mat-label>Description</mat-label>
          <textarea matInput formControlName="description" rows="3"></textarea>
        </mat-form-field>

        <mat-form-field appearance="fill" class="full-width">
          <mat-label>Status</mat-label>
          <mat-select formControlName="status">
            <mat-option value="ACTIVE">Active</mat-option>
            <mat-option value="INACTIVE">Inactive</mat-option>
            <mat-option value="COMPLETED">Completed</mat-option>
          </mat-select>
        </mat-form-field>
      </form>
    </mat-dialog-content>
    <mat-dialog-actions align="end">
      <button mat-button (click)="onCancel()">Cancel</button>
      <button mat-raised-button color="primary" (click)="onSave()" [disabled]="courseForm.invalid">
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
export class CourseDialogComponent {
  courseForm: FormGroup;

  constructor(
    private dialogRef: MatDialogRef<CourseDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private fb: FormBuilder,
    private apiService: ApiService
  ) {
    this.courseForm = this.fb.group({
      name: [data.name || '', Validators.required],
      code: [data.code || '', Validators.required],
      credits: [data.credits || 3, [Validators.required, Validators.min(1)]],
      teacher: [data.teacher || '', Validators.required],
      description: [data.description || ''],
      status: [data.status || 'ACTIVE']
    });
  }

  onSave(): void {
    if (this.courseForm.valid) {
      const courseData = this.courseForm.value;
      
      if (this.data.id) {
        this.apiService.put(`/courses/${this.data.id}`, courseData).subscribe({
          next: () => this.dialogRef.close(true),
          error: (error) => console.error('Error updating course:', error)
        });
      } else {
        this.apiService.post('/courses', courseData).subscribe({
          next: () => this.dialogRef.close(true),
          error: (error) => console.error('Error creating course:', error)
        });
      }
    }
  }

  onCancel(): void {
    this.dialogRef.close();
  }
}
