import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { User } from 'src/app/models/users.model';

/* Services */
import { AdminUsersService } from './../../services/admin-users.service';

@Component({
  selector: 'app-admin-users',
  templateUrl: './admin-users.component.html',
  styleUrls: ['./admin-users.component.css'],
})
export class AdminUsersComponent implements OnInit {
  users: User[] = [];

  user: User = { id: '', role: 'ROLE_USER' };

  headerUserDialog: string = '';
  create = false;
  userDialog = false;

  submitted = false;
  selectedUsers: User[] = [];

  constructor(
    public adminUsers: AdminUsersService,
    private confirmationService: ConfirmationService,
    private messageService: MessageService
  ) {}

  ngOnInit(): void {
    this.loadUsers();
  }

  openNew() {
    this.create = false
    this.headerUserDialog = 'Create User';
    this.user = {
      id: '',
      role: 'ROLE_USER',
      password: '12345',
      image: 'image-user',
    };
    this.submitted = false;
    this.userDialog = true;
  }

  hideDialog() {
    this.create = false
    this.userDialog = false;
    this.submitted = false;
  }

  createUserOrUpdate(): void {
    this.submitted = true;
    if (this.user.name && this.user.email) {

      if(this.create){
        this.adminUsers.createUser(this.user).subscribe((resp) => {
          this.hideDialog();
          this.loadUsers();
          this.messageService.add({
            severity: 'success',
            summary: 'Successful',
            detail: `User created`,
            life: 3000,
          });
        });
      } else {

        console.log('Current User => ', this.user);
        this.adminUsers.updateUser(this.user).subscribe( resp => {
          this.loadUsers()
          this.hideDialog()
          this.messageService.add({
            severity: 'success',
            summary: 'Successful',
            detail: 'User Updated',
            life: 3000,
          });
        })



      }

    }
  }

  updateUser(user: User): void {
      this.headerUserDialog = 'Update User';
      this.user = {...user}
      this.userDialog = true;
  }

  updateRole(user: User): void {
    this.confirmationService.confirm({
      message: 'Are you sure you want to delete ' + user?.name + '?',
      header: 'Confirm',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.adminUsers.updateRole(user.id, user.role).subscribe((resp) => {
          this.loadUsers();
          this.messageService.add({
            severity: 'success',
            summary: 'Successful',
            detail: 'User Updated',
            life: 3000,
          });
        });
      },
      reject: () => {
        this.loadUsers();
      },
    });
  }

  loadUsers(): void {
    this.adminUsers.getUsers().subscribe((resp: User[]) => {
      this.users = resp;
    });
  }

  deleteUser(user: User): void {
    this.confirmationService.confirm({
      message: 'Are you sure you want to delete ' + user?.name + '?',
      header: 'Confirm',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.adminUsers.delete(user.id).subscribe((resp) => {
          this.loadUsers();
          this.messageService.add({
            severity: 'success',
            summary: 'Successful',
            detail: 'User Deleted',
            life: 3000,
          });
        });
      },
    });
  }
}

export interface Role {
  name: string;
  code: string;
}
