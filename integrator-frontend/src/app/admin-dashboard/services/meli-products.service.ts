import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { MeliProduct } from '../models/meli-products.model';

@Injectable({
  providedIn: 'root'
})
export class MeliProductsService {

  URI_PRODUCTS: string = `${environment.uri_backend}integrator/products`

  constructor(private http: HttpClient) {
   }

  getProducts(): Observable<MeliProduct[]> {
      return this.http.get<MeliProduct[]>(`${this.URI_PRODUCTS}`)
  }

  delete(id: string){
    return this.http.delete(`${this.URI_PRODUCTS}/${id}`)
  }

  deleteBatch(ids: string[]) {
    return this.http.patch(`${this.URI_PRODUCTS}/delete/batch`, { ids })
  }
}
