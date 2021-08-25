import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MeliUsersComponent } from './meli-users.component';

describe('MeliUsersComponent', () => {
  let component: MeliUsersComponent;
  let fixture: ComponentFixture<MeliUsersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MeliUsersComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MeliUsersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
