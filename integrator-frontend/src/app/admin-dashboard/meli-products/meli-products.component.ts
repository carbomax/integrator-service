
import { MeliProductsService } from './../services/meli-products.service';
import { Component, OnInit } from '@angular/core';
import {MeliProduct, Pictures} from '../models/meli-products.model';
import { Table } from 'primeng/table';
import { ConfirmationService, MessageService } from 'primeng/api';
import { FormBuilder, Validators } from '@angular/forms';
import { ProductsCategoryService } from '../services/products-category.service';
import {log} from "util";
import {ProductsCategory} from "../models/products-category";

@Component({
  selector: 'app-meli-products',
  templateUrl: './meli-products.component.html',
  styleUrls: ['./meli-products.component.scss'],
})
export class MeliProductsComponent implements OnInit {
  meliProduct: MeliProduct;
  products: MeliProduct[] = [];
  selectedMeliProducts: MeliProduct[] = [];
  productDialog = false;
  submitted = false;
  selectedProducts: MeliProduct[] = [];
  product: MeliProduct = new MeliProduct();
  categories: any[] = [];
  create: boolean = true;

  maxFileSize = 100000
  fileUpload: any = null
  icon = 'pi pi-plus'
  productCreateFile : File = null

  productForm = this.fb.group({
    title: [null, [Validators.required]],
    description: [null, [Validators.required]],
    price: [null, [Validators.required]],
    quantity: [null, [Validators.required]],
    category: [null, [Validators.required]],
  });

  get title() {
    return this.productForm.get('title');
  }
  get description() {
    return this.productForm.get('description');
  }
  get price() {
    return this.productForm.get('price');
  }
  get quantity() {
    return this.productForm.get('quantity');
  }

  get category() {
    return this.productForm.get('category');
  }

  constructor(
    private fb: FormBuilder,
    private meliProductsService: MeliProductsService,
    private categoryService: ProductsCategoryService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) {}

  ngOnInit(): void {
    this.loadProducts();
  }

  onUpload(event: any ) {
    this.productCreateFile = event.files[0]
  }

  onSelect(event: any) {
    console.log('file', event);
    this.icon = 'pi pi-upload'
  }

  onRemove(event){
    this.icon = 'pi pi-plus'
  }
  loadProducts(): void {
    this.meliProductsService.getProducts().subscribe((resp) => {
      this.products = resp;
      console.log('products => ', this.products);
    });
  }

  searchInTable(dt: Table, event: any) {
    dt.filterGlobal(event.target.value, 'contains');
  }

  openNew() {
    this.product = new MeliProduct();
    this.submitted = false;
    this.productDialog = true;
    this.create = true;
    this.productForm.reset();
    this.categoryService.getAll().subscribe((resp) => {
      this.categories = resp.map( category =>( { label: category.name, value : category.id } ))
    });
  }

  hideDialog() {
    this.productDialog = false;
    this.submitted = false;
    this.create = false;
    this.productCreateFile = null;
  }

  saveProduct() {
    this.submitted = true;
    if (this.productForm.valid) {
      if (this.create) {
        // create product
        this.setProduct();

        console.log('Product to save', this.product);

        this.meliProductsService.create(this.product).subscribe(
          (productCreated: any) => {
           if(this.productCreateFile) {
             this.meliProductsService.uploadImage(this.productCreateFile, productCreated.id)
               .subscribe( resp => {
                 this.loadProducts();
                 this.showMessage(true, 'Image uploaded');
               }, error => {
                 this.loadProducts();
                 this.showMessage(false, 'Image not uploaded');
               })
           }

            this.showMessage(true, 'Product created');
            this.productForm.reset();
            this.product = new MeliProduct()
          },
          (error) => {
            console.log('Error created => ', error);
            this.showMessage(false, 'Product not created')
          }
        );

        this.products = [...this.products];
        this.productDialog = false;
        this.product = new MeliProduct();
      } else {
        // update product
      }
    }
  }

  private setProduct() {
    this.product.title = this.title.value;
    this.product.description = this.description.value;
    this.product.price = this.price.value;
    this.product.availableQuantity = this.quantity.value;
    this.product.category = { id: this.category.value, name: '' };
    this.product.pictures = [];
    this.product.currencyId = '';
  }

  editProduct(product: MeliProduct) {
    this.product = product
  }

  deleteProduct(meliProduct: MeliProduct){

    this.confirmationService.confirm({
      message: 'Are you sure you want to delete ' + meliProduct?.title + '?',
      header: 'Confirm',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.meliProductsService.delete(meliProduct.id).subscribe(resp => {
          this.showMessage(true, 'Product deleted')
          this.loadProducts()
        }, error => {
          this.showMessage(false, 'Product not deleted')
        })
      },
    });

  }

  deleteSelectedProducts() {}


  showMessage(success: boolean, detail: string) {
    this.messageService.add({
      severity: success ? 'success' : 'error',
      summary: success ? 'Successful' : 'Error',
      detail,
      life: 3000
    });
  }

  uploadImage(picture: Pictures) {
    return `http://localhost:9999/integrator/upload/download/products?fileName=${picture.source}`
  }
}
