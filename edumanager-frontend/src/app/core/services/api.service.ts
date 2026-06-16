import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private apiUrl = 'http://localhost:8080/api';
  private tenantId: string | null = null;

  constructor(private http: HttpClient) {
    // Load tenant ID from localStorage
    this.tenantId = localStorage.getItem('tenantId');
  }

  setTenantId(tenantId: string): void {
    this.tenantId = tenantId;
    localStorage.setItem('tenantId', tenantId);
  }

  getTenantId(): string | null {
    return this.tenantId;
  }

  private getHeaders(): HttpHeaders {
    const headers = new HttpHeaders();
    const token = localStorage.getItem('authToken');
    
    if (token) {
      headers.set('Authorization', `Bearer ${token}`);
    }
    
    if (this.tenantId) {
      headers.set('X-Tenant-ID', this.tenantId);
    }
    
    return headers;
  }

  get<T>(endpoint: string): Observable<T> {
    return this.http.get<T>(`${this.apiUrl}${endpoint}`, {
      headers: this.getHeaders()
    });
  }

  post<T>(endpoint: string, body: any): Observable<T> {
    return this.http.post<T>(`${this.apiUrl}${endpoint}`, body, {
      headers: this.getHeaders()
    });
  }

  put<T>(endpoint: string, body: any): Observable<T> {
    return this.http.put<T>(`${this.apiUrl}${endpoint}`, body, {
      headers: this.getHeaders()
    });
  }

  delete<T>(endpoint: string): Observable<T> {
    return this.http.delete<T>(`${this.apiUrl}${endpoint}`, {
      headers: this.getHeaders()
    });
  }
}
