import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StorageMeliComponent } from './storage-meli.component';

describe('StorageMeliComponent', () => {
  let component: StorageMeliComponent;
  let fixture: ComponentFixture<StorageMeliComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StorageMeliComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StorageMeliComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
