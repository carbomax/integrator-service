import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-menu-store',
  templateUrl: './menu-store.component.html',
  styleUrls: ['./menu-store.component.scss']
})
export class MenuStoreComponent implements OnInit {

  model: any[] = []

  ngOnInit(): void {
    this.model = [
      {label: 'Meli Products', icon: 'pi pi-list', routerLink: ['/meli-products']},
    ]
  }

}
