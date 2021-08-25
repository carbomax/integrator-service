import {Component} from '@angular/core';
import { AppComponent } from 'src/app/app.component';
import {AppMainComponent} from './app.main.component';

@Component({
    selector: 'app-sidebar',
      templateUrl: './app.sidebar.component.html'
})
export class AppSideBarComponent {

   // constructor(public app: AppComponent, public appMain: AppMainComponent) {}
    constructor(public app: AppComponent, public appMain: AppMainComponent) {}

}
