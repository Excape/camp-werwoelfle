import {TestBed} from '@angular/core/testing';

import {LobbyService} from './lobby.service';
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {RouterTestingModule} from "@angular/router/testing";

describe('LobbyService', () => {
  beforeEach(() =>
    TestBed.configureTestingModule({
    imports: [
      HttpClientTestingModule,
      RouterTestingModule,
    ]}));

  it('should be created', () => {
    const service: LobbyService = TestBed.get(LobbyService);
    expect(service).toBeTruthy();
  });
});
