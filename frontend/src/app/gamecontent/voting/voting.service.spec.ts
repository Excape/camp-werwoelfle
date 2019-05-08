import { TestBed } from '@angular/core/testing';

import { VotingService } from './voting.service';
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {RouterTestingModule} from "@angular/router/testing";

describe('VotingService', () => {
  beforeEach(() => TestBed.configureTestingModule({imports: [HttpClientTestingModule, RouterTestingModule]}));

  it('should be created', () => {
    const service: VotingService = TestBed.get(VotingService);
    expect(service).toBeTruthy();
  });
});
