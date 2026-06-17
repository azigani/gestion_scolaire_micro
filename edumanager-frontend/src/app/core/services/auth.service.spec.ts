import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AuthService } from './auth.service';

describe('AuthService', () => {
  let service: AuthService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AuthService]
    });
    service = TestBed.inject(AuthService);
    httpMock = TestBed.inject(HttpTestingController);
    localStorage.clear();
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should login and store token', () => {
    const mockResponse = {
      token: 'mock-jwt-token',
      tenantId: 'tenant-123',
      user: { id: 1, email: 'test@example.com' }
    };
    
    service.login('test@example.com', 'password').subscribe(response => {
      expect(response.token).toBe('mock-jwt-token');
      expect(localStorage.getItem('auth_token')).toBe('mock-jwt-token');
    });

    const req = httpMock.expectOne('/api/auth/login');
    expect(req.request.method).toBe('POST');
    req.flush(mockResponse);
  });

  it('should logout and clear token', () => {
    localStorage.setItem('auth_token', 'mock-token');
    localStorage.setItem('tenant_id', 'tenant-123');
    
    service.logout();
    
    expect(localStorage.getItem('auth_token')).toBeNull();
    expect(localStorage.getItem('tenant_id')).toBeNull();
  });

  it('should check if user is logged in', () => {
    localStorage.setItem('auth_token', 'mock-token');
    
    expect(service.isLoggedIn()).toBeTrue();
    
    localStorage.removeItem('auth_token');
    
    expect(service.isLoggedIn()).toBeFalse();
  });

  it('should get current user', () => {
    const mockUser = { id: 1, email: 'test@example.com' };
    localStorage.setItem('auth_token', 'mock-token');
    
    service.getCurrentUser().subscribe(user => {
      expect(user.email).toBe('test@example.com');
    });

    const req = httpMock.expectOne('/api/auth/me');
    req.flush(mockUser);
  });
});
