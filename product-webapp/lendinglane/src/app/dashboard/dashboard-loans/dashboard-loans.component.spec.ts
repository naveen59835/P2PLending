import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardLoansComponent } from './dashboard-loans.component';

describe('DashboardLoansComponent', () => {
  let component: DashboardLoansComponent;
  let fixture: ComponentFixture<DashboardLoansComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DashboardLoansComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashboardLoansComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
