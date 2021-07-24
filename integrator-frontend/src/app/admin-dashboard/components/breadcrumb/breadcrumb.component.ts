import { MenuItem } from 'primeng/api';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-breadcrumb',
  templateUrl: './breadcrumb.component.html',
  styleUrls: ['./breadcrumb.component.css'],
})
export class BreadcrumbComponent implements OnInit {
  items: MenuItem[] = []
  home: MenuItem = {};

  constructor() {}

  ngOnInit(): void {
    this.items = [
      { label: 'Categories' },
      { label: 'Sports' },
      { label: 'Football' },
    ]
    this.home = { icon: 'pi pi-home', routerLink: '/auth' };
  }
}
