import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LenderLoanDetailsComponent } from './lender-loan-details.component';

describe('LenderLoanDetailsComponent', () => {
  let component: LenderLoanDetailsComponent;
  let fixture: ComponentFixture<LenderLoanDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LenderLoanDetailsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LenderLoanDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
