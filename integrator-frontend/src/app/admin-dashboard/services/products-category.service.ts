import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { ProductsCategory } from '../models/products-category';

@Injectable({
  providedIn: 'root'
})
export class ProductsCategoryService {

  BASE_URI: string = `${environment.uri_backend}integrator/products/category`

  constructor(private http: HttpClient) { }

  getAll(): Observable<ProductsCategory[]> {
    return this.http.get<ProductsCategory[]>(this.BASE_URI)
  }

  save( name: string): Observable<ProductsCategory>{
      return this.http.post<ProductsCategory>(`${this.BASE_URI}`, { name })
  }

  update(category: ProductsCategory, id: string): Observable<ProductsCategory> {
    return this.http.put<ProductsCategory>(`${this.BASE_URI}/${id}`, category)
  }

  getById(id: string): Observable<ProductsCategory> {
    return this.http.get<ProductsCategory>(`${this.BASE_URI}/${id}`)
  }

  delete(id: string){
     return this.http.delete(`${this.BASE_URI}/${id}`)
  }

  deleteBatch(ids: string[]) {
    return this.http.patch(`${this.BASE_URI}/delete/batch`, { ids })
  }
}
