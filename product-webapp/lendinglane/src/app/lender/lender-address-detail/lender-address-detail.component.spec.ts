import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LenderAddressDetailComponent } from './lender-address-detail.component';

describe('LenderAddressDetailComponent', () => {
  let component: LenderAddressDetailComponent;
  let fixture: ComponentFixture<LenderAddressDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LenderAddressDetailComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LenderAddressDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
