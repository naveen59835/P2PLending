import { TestBed } from '@angular/core/testing';

import { BorrowerRegistrationService } from './borrower-registration.service';

describe('BorrowerRegistrationService', () => {
  let service: BorrowerRegistrationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BorrowerRegistrationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
