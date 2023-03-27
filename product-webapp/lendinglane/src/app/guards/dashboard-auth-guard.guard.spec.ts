import { TestBed } from '@angular/core/testing';

import { DashboardAuthGuardGuard } from './dashboard-auth-guard.guard';

describe('DashboardAuthGuardGuard', () => {
  let guard: DashboardAuthGuardGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(DashboardAuthGuardGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
