import { AdminUsersComponent } from './pages/admin-users/admin-users.component';
import { StorageMeliComponent } from './pages/storage-meli/storage-meli.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';

const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    children: [
      {
        path: 'dashboard',
        component: DashboardComponent,
      },
      {
        path: 'storage-meli',
        component: StorageMeliComponent,
      },
      {
        path: 'users',
        component: AdminUsersComponent
      },
      { path: '**', redirectTo: 'dashboard' },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AdminDashboardRoutingModule {}
