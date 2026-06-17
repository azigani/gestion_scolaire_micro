import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ApiService } from '../../core/services/api.service';

@Component({
  selector: 'app-student-dialog',
  template: `
    <h2 mat-dialog-title>{{ data.id ? 'Edit Student' : 'Add New Student' }}</h2>
    <mat-dialog-content>
      <form [formGroup]="studentForm">
        <mat-form-field appearance="fill" class="full-width">
          <mat-label>First Name</mat-label>
          <input matInput formControlName="firstName" required>
          <mat-error *ngIf="studentForm.get('firstName')?.hasError('required')">
            First name is required
          </mat-error>
        </mat-form-field>

        <mat-form-field appearance="fill" class="full-width">
          <mat-label>Last Name</mat-label>
          <input matInput formControlName="lastName" required>
          <mat-error *ngIf="studentForm.get('lastName')?.hasError('required')">
            Last name is required
          </mat-error>
        </mat-form-field>

        <mat-form-field appearance="fill" class="full-width">
          <mat-label>Email</mat-label>
          <input matInput formControlName="email" type="email" required>
          <mat-error *ngIf="studentForm.get('email')?.hasError('required')">
            Email is required
          </mat-error>
          <mat-error *ngIf="studentForm.get('email')?.hasError('email')">
            Please enter a valid email
          </mat-error>
        </mat-form-field>

        <mat-form-field appearance="fill" class="full-width">
          <mat-label>Phone</mat-label>
          <input matInput formControlName="phone">
        </mat-form-field>

        <mat-form-field appearance="fill" class="full-width">
          <mat-label>Date of Birth</mat-label>
          <input matInput [matDatepicker]="picker" formControlName="dateOfBirth">
          <mat-datepicker-toggle matSuffix [for]="picker"></mat-datepicker-toggle>
          <mat-datepicker #picker></mat-datepicker>
        </mat-form-field>

        <mat-form-field appearance="fill" class="full-width">
          <mat-label>Gender</mat-label>
          <mat-select formControlName="gender">
            <mat-option value="MALE">Male</mat-option>
            <mat-option value="FEMALE">Female</mat-option>
            <mat-option value="OTHER">Other</mat-option>
          </mat-select>
        </mat-form-field>

        <mat-form-field appearance="fill" class="full-width">
          <mat-label>Address</mat-label>
          <textarea matInput formControlName="address" rows="2"></textarea>
        </mat-form-field>

        <mat-form-field appearance="fill" class="full-width">
          <mat-label>City</mat-label>
          <input matInput formControlName="city">
        </mat-form-field>

        <mat-form-field appearance="fill" class="full-width">
          <mat-label>Country</mat-label>
          <input matInput formControlName="country">
        </mat-form-field>
      </form>
    </mat-dialog-content>
    <mat-dialog-actions align="end">
      <button mat-button (click)="onCancel()">Cancel</button>
      <button mat-raised-button color="primary" (click)="onSave()" [disabled]="studentForm.invalid">
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
export class StudentDialogComponent {
  studentForm: FormGroup;

  constructor(
    private dialogRef: MatDialogRef<StudentDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private fb: FormBuilder,
    private apiService: ApiService
  ) {
    this.studentForm = this.fb.group({
      firstName: [data.firstName || '', Validators.required],
      lastName: [data.lastName || '', Validators.required],
      email: [data.email || '', [Validators.required, Validators.email]],
      phone: [data.phone || ''],
      dateOfBirth: [data.dateOfBirth || ''],
      gender: [data.gender || ''],
      address: [data.address || ''],
      city: [data.city || ''],
      country: [data.country || '']
    });
  }

  onSave(): void {
    if (this.studentForm.valid) {
      const studentData = this.studentForm.value;
      
      if (this.data.id) {
        this.apiService.put(`/students/${this.data.id}`, studentData).subscribe({
          next: () => this.dialogRef.close(true),
          error: (error) => console.error('Error updating student:', error)
        });
      } else {
        this.apiService.post('/students', studentData).subscribe({
          next: () => this.dialogRef.close(true),
          error: (error) => console.error('Error creating student:', error)
        });
      }
    }
  }

  onCancel(): void {
    this.dialogRef.close();
  }
}
