import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BorrowerLoansComponent } from './borrower-loans.component';

describe('BorrowerLoansComponent', () => {
  let component: BorrowerLoansComponent;
  let fixture: ComponentFixture<BorrowerLoansComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BorrowerLoansComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BorrowerLoansComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
