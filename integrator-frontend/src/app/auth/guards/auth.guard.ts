import { Injectable } from '@angular/core';
import {
  CanActivate,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  UrlTree,
  Router,
} from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/core/services/auth.service';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ):
    | Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree>
    | boolean
    | UrlTree {

      if(this.authService.isAuthenticated()){
        if(this.isTokenExpirated()) {
          this.authService.logout()
          this.router.navigate(['auth/login'])
          return false;
        }
        return true
      }
      this.router.navigate(['auth/login']);
      return false;
  }


  isTokenExpirated(): boolean {
    const exp = this.authService.authenticationData.exp;
    const timeNow = new Date().getTime() / 1000;
    return +exp < +timeNow;
  }
}
