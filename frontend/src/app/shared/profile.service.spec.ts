import { TestBed } from '@angular/core/testing';

import { ProfileService } from './profile.service';
import {HttpClientTestingModule} from "@angular/common/http/testing";

describe('ProfileService', () => {
  beforeEach(() => TestBed.configureTestingModule({imports: [HttpClientTestingModule]}));

  it('should be created', () => {
    const service: ProfileService = TestBed.get(ProfileService);
    expect(service).toBeTruthy();
  });
});
