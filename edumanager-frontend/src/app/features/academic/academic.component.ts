import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ApiService } from '../../core/services/api.service';
import { CourseDialogComponent } from './course-dialog.component';

@Component({
  selector: 'app-academic',
  templateUrl: './academic.component.html',
  styleUrls: ['./academic.component.scss']
})
export class AcademicComponent implements OnInit {
  title = 'Academic Management';
  courses: any[] = [];
  displayedColumns: string[] = ['id', 'name', 'code', 'credits', 'teacher', 'status', 'actions'];
  isLoading = false;
  searchTerm = '';

  constructor(
    private apiService: ApiService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.loadCourses();
  }

  loadCourses(): void {
    this.isLoading = true;
    this.apiService.get('/courses').subscribe({
      next: (data: any) => {
        this.courses = data;
        this.isLoading = false;
      },
      error: (error: any) => {
        console.error('Error loading courses:', error);
        this.isLoading = false;
      }
    });
  }

  searchCourses(): void {
    if (this.searchTerm) {
      this.isLoading = true;
      this.apiService.get(`/courses/search?term=${this.searchTerm}`).subscribe({
        next: (data: any) => {
          this.courses = data;
          this.isLoading = false;
        },
        error: (error: any) => {
          console.error('Error searching courses:', error);
          this.isLoading = false;
        }
      });
    } else {
      this.loadCourses();
    }
  }

  openDialog(course?: any): void {
    const dialogRef = this.dialog.open(CourseDialogComponent, {
      width: '500px',
      data: course || {}
    });

    dialogRef.afterClosed().subscribe((result: any) => {
      if (result) {
        this.loadCourses();
      }
    });
  }

  deleteCourse(id: number): void {
    if (confirm('Are you sure you want to delete this course?')) {
      this.apiService.delete(`/courses/${id}`).subscribe({
        next: () => {
          this.loadCourses();
        },
        error: (error: any) => {
          console.error('Error deleting course:', error);
        }
      });
    }
  }

  getStatusClass(status: string): string {
    switch (status) {
      case 'ACTIVE': return 'status-active';
      case 'INACTIVE': return 'status-inactive';
      case 'COMPLETED': return 'status-completed';
      default: return '';
    }
  }
}
