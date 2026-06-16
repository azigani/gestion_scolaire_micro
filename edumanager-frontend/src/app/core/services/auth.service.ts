import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { map } from 'rxjs/operators';

interface LoginResponse {
  token: string;
  tenantId: string;
  user: any;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/tenant/auth';
  private currentUserSubject = new BehaviorSubject<any>(null);
  public currentUser$ = this.currentUserSubject.asObservable();

  constructor(private http: HttpClient) {
    const user = localStorage.getItem('currentUser');
    if (user) {
      this.currentUserSubject.next(JSON.parse(user));
    }
  }

  login(email: string, password: string, tenantId: string): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, {
      email,
      password,
      tenantId
    }).pipe(
      map(response => {
        localStorage.setItem('authToken', response.token);
        localStorage.setItem('tenantId', response.tenantId);
        localStorage.setItem('currentUser', JSON.stringify(response.user));
        this.currentUserSubject.next(response.user);
        return response;
      })
    );
  }

  logout(): void {
    localStorage.removeItem('authToken');
    localStorage.removeItem('tenantId');
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
  }

  isLoggedIn(): boolean {
    return !!localStorage.getItem('authToken');
  }

  getCurrentUser(): any {
    return this.currentUserSubject.value;
  }
}
