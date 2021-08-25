import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ConfirmationService, Message, MessageService } from 'primeng/api';
import { Table } from 'primeng/table';
import { DeleteBatchDto } from 'src/app/models/delete-batch.model';
import { User } from 'src/app/models/users.model';
import { AdminUsersService } from '../services/admin-users.service';

interface City {
  name: string;
  code: string;
}

@Component({
  selector: 'app-admin-users',
  templateUrl: './admin-users.component.html',
  styleUrls: ['./admin-users.component.scss']
})
export class AdminUsersComponent implements OnInit {
  users: User[] = [];
  user: User = { id: '', role: 'ROLE_USER', enabled: false };

  // Dialog fields
  headerUserDialog: string = '';
  create = false;
  userDialog = false;
  submitted = false;
  selectedUsers: User[] = [];

  // Reactive form
  userForm = this.fb.group({
    name: [this.user.name, [Validators.required, Validators.minLength(4)]],
    email: [this.user.email, [Validators.required, Validators.email]],
  });

  selectedCity: City = { name: '', code: '' };

  cities: City[] = [
    { name: 'New York', code: 'NY' },
    { name: 'Rome', code: 'RM' },
    { name: 'London', code: 'LDN' },
    { name: 'Istanbul', code: 'IST' },
    { name: 'Paris', code: 'PRS' },
  ];

  message: Message[] = [];

  constructor(
    private fb: FormBuilder,
    public adminUsers: AdminUsersService,
    private confirmationService: ConfirmationService,
    private messageService: MessageService
  ) {}

  ngOnInit(): void {
    this.loadUsers();
  }

  // Getters
  get name() {
    return this.userForm.get('name');
  }

  get email() {
    return this.userForm.get('email');
  }


  searchInTable(dt: Table, event: any) {
    dt.filterGlobal(event.target.value, 'contains')
  }

  openNew() {
    this.create = true;
    this.headerUserDialog = 'Create User';
    this.name?.setValue('');
    this.email?.setValue('');
    this.user = {
      id: '',
      role: 'ROLE_USER',
      password: '12345',
      image: 'image-user',
      enabled: false
    };
    this.submitted = false;
    this.userDialog = true;
  }

  hideDialog() {
    this.create = false;
    this.userDialog = false;
    this.submitted = false;
  }

  enableOrDisable(user: User){

    console.log('Enable:', user);


    this.confirmationService.confirm({
      message: `Are you sure you want to ${ !user.enabled ? 'enable' : 'disable'}  user?`,
      header: 'Confirm',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.adminUsers.enableOrDisable(user.id, user.enabled).subscribe(resp => {
          this.loadUsers()
          this.showMessage('success', 'Successful', resp.enabled ? 'User enabled' : 'User disabled')
        },
          (error) => {
            this.loadUsers()
            this.logAndShowError('enable', error, !user.enabled ? 'User not enabled' : 'User not disabled');
          }
        );
      },
      reject: () => {
        this.loadUsers();
      },
    });


  }

  createUserOrUpdate(): void {
    this.submitted = true;
    if (this.userForm.invalid) {
      return;
    }

    this.user.name = this.name?.value;
    this.user.email = this.email?.value;

    console.log('User to Save', this.user);

    if (this.create) {
      this.adminUsers.createUser(this.user).subscribe(
        (resp) => {
          this.hideDialog();
          this.loadUsers();
          this.showMessage('success', 'Successful', 'User created');
        },
        (error) => {
          this.logAndShowError('create', error, 'User not created');
        }
      );
    } else {
      this.adminUsers.updateUser(this.user).subscribe(
        (resp) => {
          this.loadUsers();
          this.hideDialog();
          this.showMessage('success', 'Successful', 'User updated');
        },
        (error) => {
          this.logAndShowError('update', error, 'User not updated');
        }
      );
    }
  }

  updateUser(user: User): void {
    this.headerUserDialog = 'Update User';
    this.create = false;
    this.name?.setValue(user.name);
    this.email?.setValue(user.email);
    this.user = { ...user };
    this.userDialog = true;
  }

  updateRole(user: User): void {
    this.confirmationService.confirm({
      message: 'Are you sure you want to update rol to ' + user?.name + '?',
      header: 'Confirm',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.adminUsers.updateRole(user.id, user.role).subscribe(
          (resp) => {
            this.loadUsers();
            this.showMessage('success', 'Successful', 'Rol updated');
          },
          (error) => {
            this.logAndShowError('update-role', error, 'Rol not updated');
          }
        );
      },
      reject: () => {
        this.loadUsers();
      },
    });
  }

  loadUsers(): void {
    this.adminUsers.getUsers().subscribe((resp: User[]) => {
      this.users = resp;
      console.log('Users load', this.users);

    });
  }

  deleteUser(user: User): void {
    this.confirmationService.confirm({
      message: 'Are you sure you want to delete ' + user?.name + '?',
      header: 'Confirm',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.adminUsers.delete(user.id).subscribe(
          (resp) => {
            this.loadUsers();
            this.showMessage('success', 'Successful', 'Rol deleted');
          },
          (error) => {
            this.logAndShowError('delete', error, 'User not deleted');
          }
        );
      },
    });
  }

  logAndShowError(action: string, error: any, toastDetal: string) {
    console.log(action, error);
    this.showMessage('error', 'Error', toastDetal);
  }

  showMessage(severity: string, summary: string, detail: string) {
    this.messageService.add({ severity, summary, detail });
  }

  deleteInBatch() {
    this.confirmationService.confirm({
      message: 'Are you sure you want to delete ?',
      header: 'Confirm',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.adminUsers
          .deleteBatch(this.selectedUsers.map((user) => user.id))
          .subscribe(
            (resp: DeleteBatchDto) => {
              console.log('Delete-batch: ', resp);
              this.loadUsers();
              if (!resp.errors) {
                this.showMessage('success', 'Successful', 'Users Deleted');
              } else {
                let successMessages: Message[] = [];
                let errorMessages: Message[] = [];
                this.selectedUsers
                  .filter((users) => resp.errors.includes(users.id))
                  .forEach((user) =>
                    errorMessages.push({
                      severity: 'error',
                      summary: 'Error',
                      detail: `User ${user?.name} not deleted`,
                    })
                  );

                this.selectedUsers
                  .filter((users) => !resp.errors.includes(users.id))
                  .forEach((user) =>
                    successMessages.push({
                      severity: 'success',
                      summary: 'Successful',
                      detail: `User ${user?.name} deleted`,
                    })
                  );
                this.messageService.addAll(successMessages);
                this.messageService.addAll(errorMessages);
                this.selectedUsers = [];
              }
            },
            (error) => {
              this.logAndShowError('delete-batch', error, 'Users not deleted');
            }
          );
      },
    });
  }
}

export interface Role {
  name: string;
  code: string;
}
