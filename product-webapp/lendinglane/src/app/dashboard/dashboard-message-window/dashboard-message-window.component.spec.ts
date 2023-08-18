import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardMessageWindowComponent } from './dashboard-message-window.component';

describe('DashboardMessageWindowComponent', () => {
  let component: DashboardMessageWindowComponent;
  let fixture: ComponentFixture<DashboardMessageWindowComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DashboardMessageWindowComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashboardMessageWindowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
