import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ApiService } from '../../core/services/api.service';

@Component({
  selector: 'app-invoice-dialog',
  template: `
    <h2 mat-dialog-title>{{ data.id ? 'Edit Invoice' : 'Add New Invoice' }}</h2>
    <mat-dialog-content>
      <form [formGroup]="invoiceForm">
        <mat-form-field appearance="fill" class="full-width">
          <mat-label>Invoice Number</mat-label>
          <input matInput formControlName="invoiceNumber" required>
          <mat-error *ngIf="invoiceForm.get('invoiceNumber')?.hasError('required')">
            Invoice number is required
          </mat-error>
        </mat-form-field>

        <mat-form-field appearance="fill" class="full-width">
          <mat-label>Student</mat-label>
          <input matInput formControlName="student" required>
          <mat-error *ngIf="invoiceForm.get('student')?.hasError('required')">
            Student is required
          </mat-error>
        </mat-form-field>

        <mat-form-field appearance="fill" class="full-width">
          <mat-label>Amount</mat-label>
          <input matInput type="number" formControlName="amount" required min="0">
          <mat-error *ngIf="invoiceForm.get('amount')?.hasError('required')">
            Amount is required
          </mat-error>
        </mat-form-field>

        <mat-form-field appearance="fill" class="full-width">
          <mat-label>Due Date</mat-label>
          <input matInput [matDatepicker]="picker" formControlName="dueDate">
          <mat-datepicker-toggle matSuffix [for]="picker"></mat-datepicker-toggle>
          <mat-datepicker #picker></mat-datepicker>
        </mat-form-field>

        <mat-form-field appearance="fill" class="full-width">
          <mat-label>Description</mat-label>
          <textarea matInput formControlName="description" rows="3"></textarea>
        </mat-form-field>

        <mat-form-field appearance="fill" class="full-width">
          <mat-label>Status</mat-label>
          <mat-select formControlName="status">
            <mat-option value="PENDING">Pending</mat-option>
            <mat-option value="PAID">Paid</mat-option>
            <mat-option value="OVERDUE">Overdue</mat-option>
            <mat-option value="CANCELLED">Cancelled</mat-option>
          </mat-select>
        </mat-form-field>
      </form>
    </mat-dialog-content>
    <mat-dialog-actions align="end">
      <button mat-button (click)="onCancel()">Cancel</button>
      <button mat-raised-button color="primary" (click)="onSave()" [disabled]="invoiceForm.invalid">
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
export class InvoiceDialogComponent {
  invoiceForm: FormGroup;

  constructor(
    private dialogRef: MatDialogRef<InvoiceDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private fb: FormBuilder,
    private apiService: ApiService
  ) {
    this.invoiceForm = this.fb.group({
      invoiceNumber: [data.invoiceNumber || '', Validators.required],
      student: [data.student || '', Validators.required],
      amount: [data.amount || 0, [Validators.required, Validators.min(0)]],
      dueDate: [data.dueDate || ''],
      description: [data.description || ''],
      status: [data.status || 'PENDING']
    });
  }

  onSave(): void {
    if (this.invoiceForm.valid) {
      const invoiceData = this.invoiceForm.value;
      
      if (this.data.id) {
        this.apiService.put(`/invoices/${this.data.id}`, invoiceData).subscribe({
          next: () => this.dialogRef.close(true),
          error: (error) => console.error('Error updating invoice:', error)
        });
      } else {
        this.apiService.post('/invoices', invoiceData).subscribe({
          next: () => this.dialogRef.close(true),
          error: (error) => console.error('Error creating invoice:', error)
        });
      }
    }
  }

  onCancel(): void {
    this.dialogRef.close();
  }
}
