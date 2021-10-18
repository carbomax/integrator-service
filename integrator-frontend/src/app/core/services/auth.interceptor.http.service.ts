import {
  HttpEvent,
  HttpHandler,
  HttpHeaders,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root',
})
export class AuthInterceptorHttpService implements HttpInterceptor {
  constructor(private authService: AuthService, private router: Router) {}

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    console.log('Authenticated', this.authService.isAuthenticated());

    if (this.authService.isAuthenticated()) {

      // Clone the request and set the new header in one step.
       req = req.clone({
        setHeaders: {
          Authorization: this.authService.tokenStore,
          'Content-Type': 'application/x-www-form-urlencoded',
        }
      });


    }

    console.log(req.headers);
    return next.handle(req).pipe(
      catchError((error) => {
       // this.authService.logout();
       // this.router.navigate(['auth/login']);
        return throwError(error);
      })
    );
  }
}
