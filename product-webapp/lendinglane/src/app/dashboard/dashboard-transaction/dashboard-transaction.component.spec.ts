import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardTransactionComponent } from './dashboard-transaction.component';

describe('DashboardTransactionComponent', () => {
  let component: DashboardTransactionComponent;
  let fixture: ComponentFixture<DashboardTransactionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DashboardTransactionComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashboardTransactionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
