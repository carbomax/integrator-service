import { Component } from '@angular/core';
import {PrimeNGConfig} from 'primeng/api';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  layoutMode = 'static';

  darkMenu = false;

  inputStyle = 'outlined';

  ripple = true;

  compactMode = false;
  title = 'integrator-frontend';

  constructor(private primengConfig: PrimeNGConfig) {}

  ngOnInit() {
      this.primengConfig.ripple = true;
  }
}
