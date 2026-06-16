import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {

  constructor(private snackBar: MatSnackBar) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    return next.handle(request).pipe(
      catchError((error: HttpErrorResponse) => {
        let errorMessage = 'An error occurred';
        
        if (error.error instanceof ErrorEvent) {
          errorMessage = error.error.message;
        } else {
          errorMessage = error.error?.message || `Server error: ${error.status}`;
        }

        this.snackBar.open(errorMessage, 'Close', {
          duration: 5000,
          horizontalPosition: 'right',
          verticalPosition: 'top'
        });

        return throwError(() => error);
      })
    );
  }
}
