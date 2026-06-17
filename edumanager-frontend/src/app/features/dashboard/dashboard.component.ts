import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../core/services/api.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  title = 'Dashboard';
  stats = {
    totalStudents: 0,
    totalTeachers: 0,
    totalClasses: 0,
    totalRevenue: 0
  };
  recentActivities: any[] = [];
  upcomingEvents: any[] = [];

  constructor(private apiService: ApiService) {}

  ngOnInit(): void {
    this.loadDashboardData();
  }

  loadDashboardData(): void {
    this.apiService.get('/dashboard/stats').subscribe({
      next: (data: any) => {
        this.stats = data;
      },
      error: (error) => {
        console.error('Error loading dashboard stats:', error);
      }
    });

    this.apiService.get('/dashboard/activities').subscribe({
      next: (data: any) => {
        this.recentActivities = data;
      },
      error: (error) => {
        console.error('Error loading activities:', error);
      }
    });

    this.apiService.get('/dashboard/events').subscribe({
      next: (data: any) => {
        this.upcomingEvents = data;
      },
      error: (error) => {
        console.error('Error loading events:', error);
      }
    });
  }
}
