import {inject, TestBed} from '@angular/core/testing';

import {IsLoggedInGuard} from './is-logged-in.guard';
import {RouterTestingModule} from "@angular/router/testing";

describe('IsLoggedInGuard', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [IsLoggedInGuard],
      imports: [RouterTestingModule]
    });
  });

  it('should ...', inject([IsLoggedInGuard], (guard: IsLoggedInGuard) => {
    expect(guard).toBeTruthy();
  }));
});
