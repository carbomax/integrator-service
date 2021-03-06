import { ProductCategoryComponent } from './product-category/product-category.component';
import { MeliProductsComponent } from './meli-products/meli-products.component';
import { NotFoundComponent } from './../shared/pages/not-found/not-found.component';

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DashboardDemoComponent } from '../demo/view/dashboarddemo.component';
import { AdminUsersComponent } from './admin-users/admin-users.component';
import { AppMainComponent } from './components/app.main.component';
import { MeliUsersComponent } from './meli-users/meli-users.component';

const routes: Routes = [
  {
    path: '',
    component: AppMainComponent,
    children: [
      {
        path: 'dashboard',
        component: DashboardDemoComponent,
      },
      { path: 'admin-users', component: AdminUsersComponent },
      { path: 'meli_accounts', component: MeliUsersComponent },
      { path: 'meli-products', component: MeliProductsComponent },
      { path: 'products-category', component: ProductCategoryComponent },

      {path: '**', redirectTo: 'dashboard'}
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AdminDashboardRoutingModule {}
