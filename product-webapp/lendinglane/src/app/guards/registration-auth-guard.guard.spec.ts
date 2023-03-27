import { TestBed } from '@angular/core/testing';

import { RegistrationAuthGuardGuard } from './registration-auth-guard.guard';

describe('RegistrationAuthGuardGuard', () => {
  let guard: RegistrationAuthGuardGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(RegistrationAuthGuardGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
