import { Attributes } from './../models/meli-products.model';
import { MeliProductsService } from './../services/meli-products.service';
import { Component, OnInit } from '@angular/core';
import { MeliProduct } from '../models/meli-products.model';
import { Table } from 'primeng/table';

@Component({
  selector: 'app-meli-products',
  templateUrl: './meli-products.component.html',
  styleUrls: ['./meli-products.component.scss'],
})
export class MeliProductsComponent implements OnInit {
  meliProduct: MeliProduct;
  meliProducts: MeliProduct[] = [];
  selectedMeliProducts: MeliProduct[] = [];

  constructor(private meliProductsService: MeliProductsService) {}

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts(): void {
    this.meliProductsService.getProducts().subscribe((resp) => {
      this.meliProducts = resp;
    });
  }

  searchInTable(dt: Table, event: any) {
    dt.filterGlobal(event.target.value, 'contains')
  }





  initMeliProduct() {
    this.meliProduct = {
      pictures: [],
      salesTerms: [],
      attributes: [],
      description: {},
      availableQuantity: 0,
      buyingMode: 'buy_it_now',
      categoryId: '',
      condition: 'new',
      currencyId: '',
      idPublication: '',
      listingTypeId: 'gold_special',
      price: 0,
      sellerId: '',
      siteId: '',
      subTitle: '',
      title: '',
      videoId: '',
    };
  }
}
