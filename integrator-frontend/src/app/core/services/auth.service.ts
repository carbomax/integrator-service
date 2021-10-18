import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { AuthenticationData } from '../models/authentication.data.model';
import { map } from 'rxjs/operators';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  URI = `${environment.uri_backend}oauth/token`;
  headers = new HttpHeaders({
    'Content-Type': 'application/x-www-form-urlencoded',
    'Authorization': 'Basic ' + btoa('integrator-frontend' + ':' + '12345'),
  });

  constructor(private http: HttpClient) {}

  login(email: string, password: string): Observable<AuthenticationData> {
    const params = new URLSearchParams();
    params.set('grant_type', 'password');
    params.set('username', email);
    params.set('password', password);
    return this.http
      .post<AuthenticationData>(this.URI, params.toString(), {
        headers: this.headers,
      })
      .pipe(
        map((data) => {
          this.setTokenStore(data);
          return this.authenticationData;
        })
      );
  }

  logout() {
    localStorage.removeItem('token');
  }

  isAuthenticated() {
   return this.authenticationData != null;
  }

  setTokenStore(data: any) {
    localStorage.setItem('token', 'Bearer ' + data.access_token);
  }

  get tokenStore() {
    const token = localStorage.getItem('token');
    return token ? token : null;
  }

  get authenticationData() {
    if (this.tokenInfo) {
      const authenticationData = new AuthenticationData()
      authenticationData.authorities = this.tokenInfo.authorities;
      authenticationData.enabled = this.tokenInfo.enabled;
      authenticationData.exp = this.tokenInfo.exp;
      authenticationData.jti = this.tokenInfo.jti;
      authenticationData.userName = this.tokenInfo.user_name;
      authenticationData.name = this.tokenInfo.name;
      return authenticationData;
    }
    return null;
  }

  get tokenInfo(): any {
    return this.tokenStore
      ? JSON.parse(atob(this.tokenStore.split(' ')[1].split('.')[1]))
      : null;
  }

  hasRole(role: string){
    return this.authenticationData ? this.authenticationData.authorities.includes(role) : false
  }
}
