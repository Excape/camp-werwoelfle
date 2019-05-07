import { TestBed, async, inject } from '@angular/core/testing';

import { LoginScreenGuard } from './login-screen.guard';
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";
import {RouterTestingModule} from "@angular/router/testing";

describe('LoginScreenGuard', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [LoginScreenGuard],
      imports: [
        HttpClientTestingModule,
        RouterTestingModule,
      ]
    });
  });

  it('should ...', inject([LoginScreenGuard], (guard: LoginScreenGuard) => {
    expect(guard).toBeTruthy();
  }));
});
