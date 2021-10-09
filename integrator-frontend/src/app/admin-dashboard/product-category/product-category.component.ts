import { ProductsCategoryService } from './../services/products-category.service';
import { Component, OnInit } from '@angular/core';
import { Table } from 'primeng/table';
import { ProductsCategory } from '../models/products-category';

import { FormBuilder, Validators } from '@angular/forms';
import { ConfirmationService, MessageService } from 'primeng/api';

@Component({
  selector: 'app-product-category',
  templateUrl: './product-category.component.html',
  styleUrls: ['./product-category.component.scss']
})
export class ProductCategoryComponent implements OnInit {


  headerCreateAndUpdateDialog: string = '';
  submitted = false;
  create = false;
  categoryDialog = false;


  category: ProductsCategory = { id: '', name: '' };
  selectedCategories: ProductsCategory[] = []
  categories: ProductsCategory[] = [];

  categoryForm = this.fb.group({
    name: [this.category.name, [ Validators.required, Validators.minLength(4) ]]
  })

  constructor(
    private fb: FormBuilder,
    private productsCategoryService: ProductsCategoryService,
    private confirmationService: ConfirmationService,
    private messageService: MessageService,
    ) { }


  ngOnInit(): void {
    this.initProductsCategory()
    this.load()
  }

  get name(){
    return this.categoryForm.get('name')
  }

  load(): void {
      this.productsCategoryService.getAll().subscribe( resp =>  {

        this.categories = resp
      }, error => console.log('Error get all', error)
      )
  }

  searchInTable(dt: Table, event: any) {
    dt.filterGlobal(event.target.value, 'contains')
  }

  openNew() {
    this.create = true;
    this.headerCreateAndUpdateDialog = 'Create category';
    this.name?.setValue('');
    this.initProductsCategory()
    this.submitted = false;
    this.categoryDialog = true;
  }

  createOrUpdate() {
    this.submitted = true;

    if(this.categoryForm.invalid) {
      return;
    }

    if(this.create) {

      this.productsCategoryService.save(this.name.value).subscribe( resp => {
        console.log('Create category', resp);
        this.hideDialog()
        this.load()
        this.showMessage('success', 'Successful', 'Category created');
      }, error => {
        console.log('Create category error =>', error);
        if(error.error.status == 409){
          this.logAndShowError('create', error, 'Name already exist');
        } else {
          this.logAndShowError('create category', error, 'Error creating category');
          this.hideDialog()
        }

      })
    } else {
      // to update

      if(this.categoryForm.invalid){
          return;
      }

      this.category.name = this.name.value;
      console.log('Update', this.category);
      this.productsCategoryService.update(this.category, this.category.id).subscribe( resp => {
        console.log('Update response => ', resp);
        this.load()
        this.hideDialog()
        this.showMessage('success', 'Successful', 'Category updated')
      }, error => {
        this.logAndShowError('update caegory', error, 'Category not updated')
      })

    }

  }


  update(category: ProductsCategory): void {
    this.headerCreateAndUpdateDialog = 'Update Category';
    this.create = false;
    this.name.setValue(category.name)
    this.category = { ...category };
    this.categoryDialog = true;
  }
  delete(category: ProductsCategory){

    this.confirmationService.confirm({
      message: 'Are you sure you want to delete ' + category?.name + '?',
      header: 'Confirm',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.productsCategoryService.delete(category.id).subscribe(resp => {
          this.showMessage('success', 'Successful', 'Category deleted')
          this.load()
         }, error => {
           this.logAndShowError('delete dategory', error, 'Category not deleted')
         })
      },
    });

  }

  deleteBatch(){

    this.confirmationService.confirm({
      message: 'Are you sure you want to delete those categories' + '?',
      header: 'Confirm',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.productsCategoryService.deleteBatch(this.selectedCategories.map( c => c.id)).subscribe(resp => {

          let toastDetail = 'Categories deleted'
          if(this.selectedCategories.length <= 1){
            toastDetail = 'Category deleted'
          }

          this.load()
          this.showMessage('success', 'Successful', toastDetail)
          this.selectedCategories = []

         }, error => {
           this.logAndShowError('delete dategory', error, 'Category not deleted')
         })
      },
    });

  }

  hideDialog() {
    this.create = false;
    this.categoryDialog = false;
    this.submitted = false;
    this.name.setValue('')
  }

initProductsCategory(){
    this.category = {
      id: '',
      name: ''
    }
}

logAndShowError(action: string, error: any, toastDetal: string) {
  console.log(action, error);
  this.showMessage('error', 'Error', toastDetal);
}

showMessage(severity: string, summary: string, detail: string) {
  this.messageService.add({ severity, summary, detail });
}

}
