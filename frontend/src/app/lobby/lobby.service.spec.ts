import { TestBed } from '@angular/core/testing';

import { LobbyService } from './lobby.service';
import {HttpClientTestingModule} from "@angular/common/http/testing";

describe('LobbyService', () => {
  beforeEach(() => TestBed.configureTestingModule({imports: [HttpClientTestingModule]}));

  it('should be created', () => {
    const service: LobbyService = TestBed.get(LobbyService);
    expect(service).toBeTruthy();
  });
});
