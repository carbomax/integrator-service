import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-menu-admin',
  templateUrl: './menu-admin.component.html',
  styleUrls: ['./menu-admin.component.scss']
})
export class MenuAdminComponent implements OnInit {

  model: any[] = []
  constructor() { }

  ngOnInit(): void {

    this.model = [
      {label: 'Users', icon: 'pi pi-user', routerLink: ['/admin-users']},
      {label: 'Meli Accounts', icon: 'pi pi-user', routerLink: ['/meli_accounts']},
    ]
  }

}
