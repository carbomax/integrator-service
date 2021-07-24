import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

// PrimeNg Modules
import { BreadcrumbModule } from 'primeng/breadcrumb';
import { CardModule } from 'primeng/card';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { DialogModule } from 'primeng/dialog';
import { DropdownModule } from 'primeng/dropdown';
import { FileUploadModule } from 'primeng/fileupload';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { MenuModule } from 'primeng/menu';
import { MenubarModule } from 'primeng/menubar';
import { MultiSelectModule } from 'primeng/multiselect';
import { RippleModule } from 'primeng/ripple';
import { SharedModule } from 'primeng/api';
import { TableModule } from 'primeng/table';
import { TabViewModule } from 'primeng/tabview';
import { ToastModule } from 'primeng/toast';
import { ToolbarModule} from 'primeng/toolbar';
import { VirtualScrollerModule } from 'primeng/virtualscroller';
import { ConfirmationService } from 'primeng/api';
import { MessageService } from 'primeng/api';




@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    BreadcrumbModule,
    CardModule,
    ConfirmDialogModule,
    DialogModule,
    DropdownModule,
    FileUploadModule,
    InputTextModule,
    MenubarModule,
    MenuModule,
    MultiSelectModule,
    RippleModule,
    SharedModule,
    ButtonModule,
    TableModule,
    TabViewModule,
    ToastModule,
    ToolbarModule,
    VirtualScrollerModule
  ],
  exports: [
    BreadcrumbModule,
    CardModule,
    DialogModule,
    ConfirmDialogModule,
    DropdownModule,
    FileUploadModule,
    InputTextModule,
    MenubarModule,
    MenuModule,
    MultiSelectModule,
    RippleModule,
    SharedModule,
    ButtonModule,
    TableModule,
    TabViewModule,
    ToastModule,
    ToolbarModule,
    VirtualScrollerModule
  ],
  providers: [MessageService, ConfirmationService]
})
export class PrimengModule { }
