import { MeliUser } from './../models/meli-users.model';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { DeleteBatchDto } from 'src/app/models/delete-batch.model';
import { AuthService } from 'src/app/core/services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class MeliUsersService {

  URI_USERS: string = `${environment.uri_backend}integrator/users/meli`

  constructor(private http: HttpClient, private authService: AuthService) {
      this.getAll()
  }

  // Save reference to account
  saveReferenceAccount(user: MeliUser){

    localStorage.setItem('accountReference', JSON.stringify({ referenceId: user.id , referenceUserSystem: user.idUserSystem }))
    window.open(environment.redirect_uri, '_parent')
  }

  getAccountReference(){
     return JSON.parse(localStorage.getItem('accountReference'))
  }

  authorize(code, accountReference) {
    const {referenceUserSystem, referenceId} = accountReference
    return this.http.post(`${this.URI_USERS}/authorization`, { code, idUserSystem: referenceUserSystem, idMeliUser: referenceId})
  }


  getAll(): Observable<MeliUser[]>{
    return this.http.get<MeliUser[]>(this.URI_USERS)
  }

  createUser(user: MeliUser): Observable<MeliUser> {
    return this.http.post<MeliUser>(this.URI_USERS, { name: user.name, description: user.description, idUserSystem: this.authService.authenticationData.userName})
 }

 updateUser(user: MeliUser): Observable<MeliUser> {
  return this.http.put<MeliUser>(`${this.URI_USERS}/${user.id}`, { name: user.name, description: user.description})
}

enableOrDisable(id: string, enabled: boolean): Observable<MeliUser>{
  return this.http.put<MeliUser>(`${this.URI_USERS}/enable/${id}/${enabled}`, {})
}

delete(id: string) {
  return this.http.delete(`${this.URI_USERS}/${id}`)
}

deleteBatch(ids: string[]): Observable<DeleteBatchDto>{
  return this.http.patch<DeleteBatchDto>(`${this.URI_USERS}/delete-batch`, { ids })
}

}
