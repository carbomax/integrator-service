import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AppConfigComponent } from './components/config/app.config.component';
import { DashboardDemoComponent } from '../demo/view/dashboarddemo.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { PrimengModule } from '../primeng/primeng.module';
import { AdminDashboardRoutingModule } from './admin-dashboard-routing.module';
import { AppTopbarComponent } from './components/app.topbar.component';
import { AppMenuitemComponent } from './components/app.menuitem.component';
import { AppSidebartabcontentComponent } from './components/app.sidebartabcontent.component';
import { AppMenuComponent } from './components/app.menu.component';
import { AppFooterComponent } from './components/app.footer.component';
import { AppSideBarComponent } from './components/app.sidebar.component';
import { AppMainComponent } from './components/app.main.component';
import { MenuAdminComponent } from './components/menus/menu-admin/menu-admin.component';
import { AdminUsersComponent } from './admin-users/admin-users.component';
import { SharedModule } from '../shared/shared.module';
import { MeliUsersComponent } from './meli-users/meli-users.component';

@NgModule({
  declarations: [
    AppTopbarComponent,
    AppMenuitemComponent,
    AppSidebartabcontentComponent,
    AppMenuComponent,
    AppMenuitemComponent,
    AppConfigComponent,
    DashboardDemoComponent,
    AppFooterComponent,
    AppSideBarComponent,
    AppMainComponent,
    MenuAdminComponent,
    AdminUsersComponent,
    MeliUsersComponent
  ],
  imports: [
    CommonModule,
    PrimengModule,
    SharedModule,
    FormsModule,
    ReactiveFormsModule,
    AdminDashboardRoutingModule
  ]
})
export class AdminDashboardModule { }