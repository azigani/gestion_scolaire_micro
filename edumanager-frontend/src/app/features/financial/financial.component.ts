import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ApiService } from '../../core/services/api.service';
import { InvoiceDialogComponent } from './invoice-dialog.component';

@Component({
  selector: 'app-financial',
  templateUrl: './financial.component.html',
  styleUrls: ['./financial.component.scss']
})
export class FinancialComponent implements OnInit {
  title = 'Financial Management';
  invoices: any[] = [];
  displayedColumns: string[] = ['id', 'invoiceNumber', 'student', 'amount', 'dueDate', 'status', 'actions'];
  isLoading = false;
  searchTerm = '';

  constructor(
    private apiService: ApiService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.loadInvoices();
  }

  loadInvoices(): void {
    this.isLoading = true;
    this.apiService.get('/invoices').subscribe({
      next: (data: any) => {
        this.invoices = data;
        this.isLoading = false;
      },
      error: (error: any) => {
        console.error('Error loading invoices:', error);
        this.isLoading = false;
      }
    });
  }

  searchInvoices(): void {
    if (this.searchTerm) {
      this.isLoading = true;
      this.apiService.get(`/invoices/search?term=${this.searchTerm}`).subscribe({
        next: (data: any) => {
          this.invoices = data;
          this.isLoading = false;
        },
        error: (error: any) => {
          console.error('Error searching invoices:', error);
          this.isLoading = false;
        }
      });
    } else {
      this.loadInvoices();
    }
  }

  openDialog(invoice?: any): void {
    const dialogRef = this.dialog.open(InvoiceDialogComponent, {
      width: '500px',
      data: invoice || {}
    });

    dialogRef.afterClosed().subscribe((result: any) => {
      if (result) {
        this.loadInvoices();
      }
    });
  }

  deleteInvoice(id: number): void {
    if (confirm('Are you sure you want to delete this invoice?')) {
      this.apiService.delete(`/invoices/${id}`).subscribe({
        next: () => {
          this.loadInvoices();
        },
        error: (error: any) => {
          console.error('Error deleting invoice:', error);
        }
      });
    }
  }

  getStatusClass(status: string): string {
    switch (status) {
      case 'PAID': return 'status-paid';
      case 'PENDING': return 'status-pending';
      case 'OVERDUE': return 'status-overdue';
      case 'CANCELLED': return 'status-cancelled';
      default: return '';
    }
  }
}
