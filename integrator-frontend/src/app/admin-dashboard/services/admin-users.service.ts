import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

// models
import { DeleteBatchDto } from 'src/app/models/delete-batch.model';
import { Role } from 'src/app/models/role.model';
import { User } from 'src/app/models/users.model';

// rxjs
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdminUsersService {
  roles: Role[] = [
    { name: 'Administrator', code: 'ROLE_ADMIN'},
    { name: 'User', code: 'ROLE_USER'}
  ]

  URI_USERS: string = `${environment.uri_backend}integrator/user/user-system`
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

   deleteBatch(ids: string[]): Observable<DeleteBatchDto>{
      return this.http.patch<DeleteBatchDto>(`${this.URI_USERS}/delete-batch`, { ids })
   }

   enableOrDisable(id: string, enabled: boolean): Observable<User>{
      return this.http.put<User>(`${this.URI_USERS}/enable/${id}/${enabled}`, {})
   }

}
