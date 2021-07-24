import { PrimengModule } from './../primeng/primeng.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';



@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    PrimengModule,
    FormsModule
  ],
  exports: [
    PrimengModule,
    FormsModule
  ]
})
export class SharedModule { }
