import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LenderLoansComponent } from './lender-loans.component';

describe('LenderLoansComponent', () => {
  let component: LenderLoansComponent;
  let fixture: ComponentFixture<LenderLoansComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LenderLoansComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LenderLoansComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
