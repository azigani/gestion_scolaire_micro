import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ApiService } from '../../core/services/api.service';
import { ClassDialogComponent } from './class-dialog.component';

@Component({
  selector: 'app-classes',
  templateUrl: './classes.component.html',
  styleUrls: ['./classes.component.scss']
})
export class ClassesComponent implements OnInit {
  title = 'Classes Management';
  classes: any[] = [];
  displayedColumns: string[] = ['id', 'name', 'grade', 'teacher', 'capacity', 'enrolled', 'status', 'actions'];
  isLoading = false;
  searchTerm = '';

  constructor(
    private apiService: ApiService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.loadClasses();
  }

  loadClasses(): void {
    this.isLoading = true;
    this.apiService.get('/classes').subscribe({
      next: (data: any) => {
        this.classes = data;
        this.isLoading = false;
      },
      error: (error: any) => {
        console.error('Error loading classes:', error);
        this.isLoading = false;
      }
    });
  }

  searchClasses(): void {
    if (this.searchTerm) {
      this.isLoading = true;
      this.apiService.get(`/classes/search?term=${this.searchTerm}`).subscribe({
        next: (data: any) => {
          this.classes = data;
          this.isLoading = false;
        },
        error: (error: any) => {
          console.error('Error searching classes:', error);
          this.isLoading = false;
        }
      });
    } else {
      this.loadClasses();
    }
  }

  openDialog(classData?: any): void {
    const dialogRef = this.dialog.open(ClassDialogComponent, {
      width: '500px',
      data: classData || {}
    });

    dialogRef.afterClosed().subscribe((result: any) => {
      if (result) {
        this.loadClasses();
      }
    });
  }

  deleteClass(id: number): void {
    if (confirm('Are you sure you want to delete this class?')) {
      this.apiService.delete(`/classes/${id}`).subscribe({
        next: () => {
          this.loadClasses();
        },
        error: (error: any) => {
          console.error('Error deleting class:', error);
        }
      });
    }
  }

  getStatusClass(status: string): string {
    switch (status) {
      case 'ACTIVE': return 'status-active';
      case 'INACTIVE': return 'status-inactive';
      case 'ARCHIVED': return 'status-archived';
      default: return '';
    }
  }
}
