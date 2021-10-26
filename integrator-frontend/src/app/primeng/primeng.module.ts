import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ConfirmPopupModule } from 'primeng/confirmpopup';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { DialogModule } from 'primeng/dialog';
import { DropdownModule } from 'primeng/dropdown';
import { FileUploadModule } from 'primeng/fileupload';
import { SidebarModule } from 'primeng/sidebar';
import { ListboxModule } from 'primeng/listbox';
import { MegaMenuModule } from 'primeng/megamenu';
import { MenuModule } from 'primeng/menu';
import { MenubarModule } from 'primeng/menubar';
import { ButtonModule } from 'primeng/button';
import { RadioButtonModule } from 'primeng/radiobutton';
import { InputNumberModule } from 'primeng/inputnumber';
import { InputMaskModule } from 'primeng/inputmask';
import { InputSwitchModule } from 'primeng/inputswitch';
import { InputTextModule } from 'primeng/inputtext';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { ProgressBarModule } from 'primeng/progressbar';
import { TableModule } from 'primeng/table';
import { TabViewModule } from 'primeng/tabview';
import { ToastModule } from 'primeng/toast';
import { ToolbarModule } from 'primeng/toolbar';
import { PanelModule } from 'primeng/panel';
import { CheckboxModule } from 'primeng/checkbox';

import { ConfirmationService, MessageService } from 'primeng/api';



@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    ConfirmDialogModule,
    DialogModule,
    DropdownModule,
    FileUploadModule,
    SidebarModule,
    ListboxModule,
    MegaMenuModule,
    MenuModule,
    MenubarModule,
    ButtonModule,
    RadioButtonModule,
    InputNumberModule,
    InputMaskModule,
    InputSwitchModule,
    InputTextModule,
    InputTextareaModule,
    ProgressBarModule,
    TableModule,
    TabViewModule,
    ToastModule,
    ToolbarModule,
    PanelModule,
    CheckboxModule,
    ConfirmPopupModule,
  ],
  exports: [
    ConfirmDialogModule,
    DialogModule,
    DropdownModule,
    FileUploadModule,
    SidebarModule,
    ListboxModule,
    MegaMenuModule,
    MenuModule,
    MenubarModule,
    ButtonModule,
    RadioButtonModule,
    InputNumberModule,
    InputMaskModule,
    InputSwitchModule,
    InputTextModule,
    InputTextareaModule,
    ProgressBarModule,
    TableModule,
    TabViewModule,
    ToastModule,
    ToolbarModule,
    PanelModule,
    CheckboxModule,
    ConfirmPopupModule,
  ],
  providers: [ConfirmationService, MessageService]
})
export class PrimengModule {}
