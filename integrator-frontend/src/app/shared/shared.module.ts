import { PrimengModule } from './../primeng/primeng.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NotFoundComponent } from './pages/not-found/not-found.component';
import { ReactiveFormsModule } from '@angular/forms';



@NgModule({
  declarations: [NotFoundComponent],
  imports: [
    CommonModule,
    PrimengModule
  ]
})
export class SharedModule { }
