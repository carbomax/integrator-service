import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MeliProductsComponent } from './meli-products.component';

describe('MeliProductsComponent', () => {
  let component: MeliProductsComponent;
  let fixture: ComponentFixture<MeliProductsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MeliProductsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MeliProductsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
