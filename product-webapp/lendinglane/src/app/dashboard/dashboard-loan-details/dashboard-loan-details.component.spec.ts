import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardLoanDetailsComponent } from './dashboard-loan-details.component';

describe('DashboardLoanDetailsComponent', () => {
  let component: DashboardLoanDetailsComponent;
  let fixture: ComponentFixture<DashboardLoanDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DashboardLoanDetailsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashboardLoanDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
