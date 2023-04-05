import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LenderTransactionComponent } from './lender-transaction.component';

describe('LenderTransactionComponent', () => {
  let component: LenderTransactionComponent;
  let fixture: ComponentFixture<LenderTransactionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LenderTransactionComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LenderTransactionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
