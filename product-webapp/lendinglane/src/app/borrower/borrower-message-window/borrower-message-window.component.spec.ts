import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BorrowerMessageWindowComponent } from './borrower-message-window.component';

describe('BorrowerMessageWindowComponent', () => {
  let component: BorrowerMessageWindowComponent;
  let fixture: ComponentFixture<BorrowerMessageWindowComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BorrowerMessageWindowComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BorrowerMessageWindowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
