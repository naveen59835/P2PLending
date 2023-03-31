import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LenderMessageWindowComponent } from './lender-message-window.component';

describe('LenderMessageWindowComponent', () => {
  let component: LenderMessageWindowComponent;
  let fixture: ComponentFixture<LenderMessageWindowComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LenderMessageWindowComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LenderMessageWindowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
