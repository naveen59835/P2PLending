import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BorrowerTransactionComponent } from './borrower-transaction.component';

describe('BorrowerTransactionComponent', () => {
  let component: BorrowerTransactionComponent;
  let fixture: ComponentFixture<BorrowerTransactionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BorrowerTransactionComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BorrowerTransactionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
