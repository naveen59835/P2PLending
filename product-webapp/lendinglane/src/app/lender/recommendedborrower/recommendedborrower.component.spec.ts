import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecommendedborrowerComponent } from './recommendedborrower.component';

describe('RecommendedborrowerComponent', () => {
  let component: RecommendedborrowerComponent;
  let fixture: ComponentFixture<RecommendedborrowerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RecommendedborrowerComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RecommendedborrowerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
