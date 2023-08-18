import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LenderMessagesComponent } from './lender-messages.component';

describe('LenderMessagesComponent', () => {
  let component: LenderMessagesComponent;
  let fixture: ComponentFixture<LenderMessagesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LenderMessagesComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LenderMessagesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
