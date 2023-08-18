import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BorrowerMessagesComponent } from './borrower-messages.component';

describe('BorrowerMessagesComponent', () => {
  let component: BorrowerMessagesComponent;
  let fixture: ComponentFixture<BorrowerMessagesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BorrowerMessagesComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BorrowerMessagesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
