import { Observable } from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { MeliProduct } from '../models/meli-products.model';

@Injectable({
  providedIn: 'root'
})
export class MeliProductsService {

  URI_PRODUCTS: string = `${environment.uri_backend}integrator/products`
  URI_UPLOAD: string = `${environment.uri_backend}integrator/upload/products`

  constructor(private http: HttpClient) {
   }

  getProducts(): Observable<MeliProduct[]> {
      return this.http.get<MeliProduct[]>(`${this.URI_PRODUCTS}`)
  }

  create(product: MeliProduct) {

    return this.http.post(`${this.URI_PRODUCTS}`, product)
  }

  delete(id: string){
    return this.http.delete(`${this.URI_PRODUCTS}/${id}`)
  }

  deleteBatch(ids: string[]) {
    return this.http.patch(`${this.URI_PRODUCTS}/delete/batch`, { ids })
  }

  uploadImage(productCreateFile: File, id: string) {
    let formData = new FormData()
    formData.append('files',productCreateFile, productCreateFile.name)

    let headers = new HttpHeaders();
    headers = headers.delete('Content-Type');
    return this.http.post(`${this.URI_UPLOAD}/${id}`, formData , { headers: headers, reportProgress: true })
  }
}
