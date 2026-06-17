import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ApiService } from './api.service';

describe('ApiService', () => {
  let service: ApiService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ApiService]
    });
    service = TestBed.inject(ApiService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should make GET request with tenant header', () => {
    const mockData = { id: 1, name: 'Test' };
    
    service.get('/test').subscribe(data => {
      expect(data).toEqual(mockData);
    });

    const req = httpMock.expectOne('/test');
    expect(req.request.headers.get('X-Tenant-ID')).toBe('default-tenant');
    req.flush(mockData);
  });

  it('should make POST request with tenant header', () => {
    const mockData = { id: 1, name: 'Test' };
    const body = { name: 'Test' };
    
    service.post('/test', body).subscribe(data => {
      expect(data).toEqual(mockData);
    });

    const req = httpMock.expectOne('/test');
    expect(req.request.headers.get('X-Tenant-ID')).toBe('default-tenant');
    req.flush(mockData);
  });

  it('should make PUT request with tenant header', () => {
    const mockData = { id: 1, name: 'Updated' };
    const body = { name: 'Updated' };
    
    service.put('/test/1', body).subscribe(data => {
      expect(data).toEqual(mockData);
    });

    const req = httpMock.expectOne('/test/1');
    expect(req.request.headers.get('X-Tenant-ID')).toBe('default-tenant');
    req.flush(mockData);
  });

  it('should make DELETE request with tenant header', () => {
    service.delete('/test/1').subscribe();
    
    const req = httpMock.expectOne('/test/1');
    expect(req.request.headers.get('X-Tenant-ID')).toBe('default-tenant');
    req.flush(null);
  });
});
