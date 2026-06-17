import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HTTP_INTERCEPTORS, HttpClient } from '@angular/common/http';
import { throwError } from 'rxjs';

import { ErrorInterceptor } from './error.interceptor';

describe('ErrorInterceptor', () => {
  let http: HttpClient;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        {
          provide: HTTP_INTERCEPTORS,
          useClass: ErrorInterceptor,
          multi: true
        }
      ]
    });
    http = TestBed.inject(HttpClient);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should handle 401 error', () => {
    http.get('/api/test').subscribe({
      error: error => {
        expect(error.status).toBe(401);
      }
    });

    const req = httpMock.expectOne('/api/test');
    req.flush('Unauthorized', { status: 401, statusText: 'Unauthorized' });
  });

  it('should handle 403 error', () => {
    http.get('/api/test').subscribe({
      error: error => {
        expect(error.status).toBe(403);
      }
    });

    const req = httpMock.expectOne('/api/test');
    req.flush('Forbidden', { status: 403, statusText: 'Forbidden' });
  });

  it('should handle 404 error', () => {
    http.get('/api/test').subscribe({
      error: error => {
        expect(error.status).toBe(404);
      }
    });

    const req = httpMock.expectOne('/api/test');
    req.flush('Not Found', { status: 404, statusText: 'Not Found' });
  });

  it('should handle 500 error', () => {
    http.get('/api/test').subscribe({
      error: error => {
        expect(error.status).toBe(500);
      }
    });

    const req = httpMock.expectOne('/api/test');
    req.flush('Internal Server Error', { status: 500, statusText: 'Internal Server Error' });
  });
});
