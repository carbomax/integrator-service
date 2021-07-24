import { Role } from './../pages/admin-users/admin-users.component';

import { environment } from './../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from 'src/app/models/users.model';

@Injectable({
  providedIn: 'root'
})
export class AdminUsersService {

  roles: Role[] = [
    { name: 'Administrator', code: 'ROLE_ADMIN'},
    { name: 'User', code: 'ROLE_USER'}
  ]

  URI_USERS: string = `${environment.uri_backend}api/users`
  constructor(private http: HttpClient) {
      this.getUsers()
   }

   createUser(user: User): Observable<User> {
      return this.http.post<User>(this.URI_USERS, user)
   }

   updateUser(user: User): Observable<User> {
      return this.http.put<User>(`${this.URI_USERS}/${user.id}`, user)
   }

   updateRole(id: string, role: string): Observable<User> {
      return this.http.put<User>(`${this.URI_USERS}/${id}/${role}`, {})
   }

   getUsers(): Observable<User[]> {
     return this.http.get<User[]>(this.URI_USERS)
   }

   delete(id: string) {
     return this.http.delete(`${this.URI_USERS}/${id}`)
   }
}
