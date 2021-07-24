import { SharedModule } from './../shared/shared.module';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeComponent } from './pages/home/home.component';
import { AdminDashboardRoutingModule } from './admin-dashboard-routing.module';
import { StorageMeliComponent } from './pages/storage-meli/storage-meli.component';
import { BreadcrumbComponent } from './components/breadcrumb/breadcrumb.component';
import { HeaderComponent } from './components/header/header.component';
import { AdminUsersComponent } from './pages/admin-users/admin-users.component';



@NgModule({
  declarations: [
    HomeComponent,
    DashboardComponent,
    StorageMeliComponent,
    BreadcrumbComponent,
    HeaderComponent,
    AdminUsersComponent
  ],
  imports: [
    CommonModule,
    AdminDashboardRoutingModule,
    SharedModule
  ]
})
export class AdminDashboardModule { }
