import {
  HttpEvent,
  HttpHandler,
  HttpHeaders,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root',
})
export class AuthInterceptorHttpService implements HttpInterceptor {
  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    if (this.authService.isAuthenticated()) {
      // Clone the request and set the new header in one step.
      req = req.clone({
        setHeaders: {
          Authorization: this.authService.tokenStore
        },
      });
    }

    return next.handle(req).pipe(
      catchError((error) => {
        console.log(error);

        const { status } = error;

        if (status === 401) {

          if (this.authService.isAuthenticated()) {
            this.authService.logout();
          }
          this.router.navigate(['auth/login']).then(r => console.log('Authentication errors', error))
        }

        if (status === 403) {
          console.log(`Forbiden access. Hi ${this.authService.authenticationData.name} does not access at this resource`);
          this.router.navigate(['dashboard']);
        }

        return throwError(error);
      })
    );
  }
}
