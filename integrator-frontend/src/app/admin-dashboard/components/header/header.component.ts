import { MenuItem } from 'primeng/api';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent implements OnInit {
  items: MenuItem[] = [];
  profileMenu: MenuItem[] = [];
  breadcrumbItems: MenuItem[] = [
    { label: 'Categories' },
    { label: 'Sports' },
    { label: 'Football' },
  ];

  constructor() {}

  ngOnInit(): void {
    this.profileMenu = [
      { label: 'Profile', icon: 'pi pi-user' },
      { separator: true },
      { label: 'Sign out', icon: 'pi pi-sign-out' , routerLink: '/auth/login'},
    ];

    this.items = [
      {
        label: 'Store',
        icon: 'pi pi-inbox',
        items: [
          {
            label: 'New',
            icon: 'pi pi-fw pi-plus',
            items: [
              {
                label: 'Bookmark',
                icon: 'pi pi-fw pi-bookmark',
              },
              {
                label: 'Video',
                icon: 'pi pi-fw pi-video',
              },
            ],
          },
          {
            label: 'Delete',
            icon: 'pi pi-fw pi-trash',
          },
          {
            separator: true,
          },
          {
            label: 'Export',
            icon: 'pi pi-fw pi-external-link',
          },
        ],
      },
      // {
      //     label:'Edit',
      //     icon:'pi pi-fw pi-pencil',
      //     items:[
      //         {
      //             label:'Left',
      //             icon:'pi pi-fw pi-align-left'
      //         },
      //         {
      //             label:'Right',
      //             icon:'pi pi-fw pi-align-right'
      //         },
      //         {
      //             label:'Center',
      //             icon:'pi pi-fw pi-align-center'
      //         },
      //         {
      //             label:'Justify',
      //             icon:'pi pi-fw pi-align-justify'
      //         },

      //     ]
      // },
      {
        label: 'Users',
        icon: 'pi pi-fw pi-user',
        routerLink: '/admin-dashboard/users',
        items: [
          // {
          //   label: 'New',
          //   icon: 'pi pi-fw pi-user-plus',
          // },
          // {
          //   label: 'Delete',
          //   icon: 'pi pi-fw pi-user-minus',
          // },
          {
            label: 'System users',
            icon: 'pi pi-fw pi-users',
            routerLink: '/admin-dashboard/users',
            // items:[
            // {
            //     label:'Filter',
            //     icon:'pi pi-fw pi-filter',
            //     items:[
            //         {
            //             label:'Print',
            //             icon:'pi pi-fw pi-print'
            //         }
            //     ]
            // },
            // {
            //     icon:'pi pi-fw pi-bars',
            //     label:'List',
            //     routerLink: '/admin-dashboard/users'
            // }
            // ]
          },
        ],
      },
      {
        label: 'Events',
        icon: 'pi pi-fw pi-calendar',
        items: [
          {
            label: 'Edit',
            icon: 'pi pi-fw pi-pencil',
            items: [
              {
                label: 'Save',
                icon: 'pi pi-fw pi-calendar-plus',
              },
              {
                label: 'Delete',
                icon: 'pi pi-fw pi-calendar-minus',
              },
            ],
          },
          {
            label: 'Archieve',
            icon: 'pi pi-fw pi-calendar-times',
            items: [
              {
                label: 'Remove',
                icon: 'pi pi-fw pi-calendar-minus',
              },
            ],
          },
        ],
      },
    ];
  }
}
