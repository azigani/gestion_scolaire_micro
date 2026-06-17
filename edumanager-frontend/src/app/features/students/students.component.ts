import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ApiService } from '../../core/services/api.service';
import { StudentDialogComponent } from './student-dialog.component';

@Component({
  selector: 'app-students',
  templateUrl: './students.component.html',
  styleUrls: ['./students.component.scss']
})
export class StudentsComponent implements OnInit {
  title = 'Students Management';
  students: any[] = [];
  displayedColumns: string[] = ['id', 'firstName', 'lastName', 'email', 'phone', 'status', 'actions'];
  isLoading = false;
  searchTerm = '';

  constructor(
    private apiService: ApiService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.loadStudents();
  }

  loadStudents(): void {
    this.isLoading = true;
    this.apiService.get('/students').subscribe({
      next: (data: any) => {
        this.students = data;
        this.isLoading = false;
      },
      error: (error: any) => {
        console.error('Error loading students:', error);
        this.isLoading = false;
      }
    });
  }

  searchStudents(): void {
    if (this.searchTerm) {
      this.isLoading = true;
      this.apiService.get(`/students/search?term=${this.searchTerm}`).subscribe({
        next: (data: any) => {
          this.students = data;
          this.isLoading = false;
        },
        error: (error: any) => {
          console.error('Error searching students:', error);
          this.isLoading = false;
        }
      });
    } else {
      this.loadStudents();
    }
  }

  openDialog(student?: any): void {
    const dialogRef = this.dialog.open(StudentDialogComponent, {
      width: '500px',
      data: student || {}
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.loadStudents();
      }
    });
  }

  deleteStudent(id: number): void {
    if (confirm('Are you sure you want to delete this student?')) {
      this.apiService.delete(`/students/${id}`).subscribe({
        next: () => {
          this.loadStudents();
        },
        error: (error: any) => {
          console.error('Error deleting student:', error);
        }
      });
    }
  }

  getStatusClass(status: string): string {
    switch (status) {
      case 'ACTIVE': return 'status-active';
      case 'INACTIVE': return 'status-inactive';
      case 'GRADUATED': return 'status-graduated';
      default: return '';
    }
  }
}
