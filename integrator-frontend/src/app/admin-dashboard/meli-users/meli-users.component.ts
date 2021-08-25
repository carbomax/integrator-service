
import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ConfirmationService, Message, MessageService } from 'primeng/api';
import { Table } from 'primeng/table';
import { DeleteBatchDto } from 'src/app/models/delete-batch.model';
import { MeliUser } from '../models/meli-users.model';
import { MeliUsersService } from '../services/meli-users.service';

@Component({
  selector: 'app-meli-users',
  templateUrl: './meli-users.component.html',
  styleUrls: ['./meli-users.component.scss'],
})
export class MeliUsersComponent implements OnInit {

  joinClass: string = 'p-button-rounded p-button-warning'
  authorizedClass: string = 'p-button-rounded p-button-secondary'
  joinTitle: string = 'Join to Meli'
  authorizTitle: string = 'Account authorized'

  constructor(
    public meliUserService: MeliUsersService,
    private fb: FormBuilder,
    private confirmationService: ConfirmationService,
    private messageService: MessageService,
    private activateRoute: ActivatedRoute,
    private router: Router
  ) {

    this.activateRoute.queryParams.subscribe( resp => {
      console.log('queryParams =>', resp);
      const code =resp.code
      const reference = this.meliUserService.getAccountReference()
      if(code && reference) {
          this.meliUserService.authorize(code, reference).subscribe( resp => {
              console.log('AUTHORIZED', resp);
             this.router.navigate(['/meli_accounts'])
          },
          error => {
            console.log('UNAUTHORIZED', error);

          })
      }
    })
  }

  users: MeliUser[] = [];
  user: MeliUser = { id: '', idUserSystem: '', idUser: '', name: '', description: '', enabled: false };

  // Dialog fields
  headerUserDialog: string = '';
  create = false;
  userDialog = false;
  submitted = false;
  selectedUsers: MeliUser[] = [];

  // Reactive form
  userForm = this.fb.group({
    name: [this.user.name, [Validators.required, Validators.minLength(4)]],
    description: [this.user.description, [Validators.required]],
  });

  message: Message[] = [];

  ngOnInit(): void {
    this.loadUsers();
  }

  redirect(user: MeliUser) {
      console.log(user);
      this.meliUserService.saveReferenceAccount(user)
  }

  // Getters
  get name() {
    return this.userForm.get('name');
  }

  get description() {
    return this.userForm.get('description');
  }

  searchInTable(dt: Table, event: any) {
    dt.filterGlobal(event.target.value, 'contains');
  }

  loadUsers(): void {
    this.meliUserService.getAll().subscribe((resp: MeliUser[]) => {
      this.users = resp;
      console.log('Users load', this.users);
    });
  }

  openNew() {
    this.create = true;
    this.headerUserDialog = 'Create Meli Account';
    this.name?.setValue('');
    this.description?.setValue('');
    this.user = { id: '', idUserSystem: '', idUser: '', name: '', description: '', enabled: false };

    this.submitted = false;
    this.userDialog = true;
  }

  hideDialog() {
    this.create = false;
    this.userDialog = false;
    this.submitted = false;
  }

  enableOrDisable(user: MeliUser) {
    this.confirmationService.confirm({
      message: `Are you sure you want to ${ !user.enabled ? 'enable' : 'disable'}  account?`,
      header: 'Confirm',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.meliUserService.enableOrDisable(user.id, user.enabled).subscribe(resp => {
          this.loadUsers()
          this.showMessage('success', 'Successful', resp.enabled ? 'Account enabled' : 'Account disabled')
        },
          (error) => {
            this.loadUsers()
            this.logAndShowError('enable', error, !user.enabled ? 'Account not enabled' : 'Account not disabled');
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
    this.user.description = this.description?.value;

    console.log('User to Save', this.user);

    if (this.create) {
      this.meliUserService.createUser(this.user).subscribe(
        (resp) => {
          this.hideDialog();
          this.loadUsers();
          this.showMessage('success', 'Successful', 'User created');
        },
        (error) => {
          if (error.status === 409) {
            this.showMessage(
              'error',
              'error',
              `Account name: ${this.name?.value} already exist`
            );
          } else {
            this.logAndShowError('create', error, 'Account not created');
          }
        }
      );
    } else {
      this.meliUserService.updateUser(this.user).subscribe(
        (resp) => {
          this.loadUsers();
          this.hideDialog();
          this.showMessage('success', 'Successful', 'User updated');
        },
        (error) => {
          if (error.status === 409) {
            this.showMessage(
              'error',
              'error',
              `Name: ${this.name?.value} already exist`
            );
          } else if (error.status === 404) {
            this.showMessage(
              'error',
              'error',
              `Account name: ${this.name?.value} not found`
            );
          } else {
            this.logAndShowError('create', error, 'User not updated');
          }
        }
      );
    }
  }

  updateUser(user: MeliUser): void {
    this.headerUserDialog = 'Update Meli Account';
    this.create = false;
    this.name?.setValue(user.name);
    this.description?.setValue(user.description);
    this.user = { ...user };
    this.userDialog = true;
  }

  deleteInBatch() {
      this.confirmationService.confirm({
      message: 'Are you sure you want to delete ?',
      header: 'Confirm',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.meliUserService
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

  deleteUser(user: MeliUser): void {
      this.confirmationService.confirm({
      message: 'Are you sure you want to delete ' + user?.name + '?',
      header: 'Confirm',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.meliUserService.delete(user.id).subscribe(
          (resp) => {
            this.loadUsers();
            this.showMessage('success', 'Successful', 'Account deleted');
          },
          (error) => {
            this.logAndShowError('delete', error, 'Account not deleted');
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
}
