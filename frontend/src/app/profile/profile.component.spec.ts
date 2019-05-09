import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ProfileComponent} from './profile.component';
import {MaterialModule} from "../material/material.module";
import {RouterTestingModule} from "@angular/router/testing";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {MessageService} from "../shared/message.service";
import {ProfileService} from "../shared/profile.service";
import {Identity, Profile} from "../shared/model/dtos";
import {FormsModule} from "@angular/forms";

describe('ProfileComponent', () => {
  let component: ProfileComponent;
  let fixture: ComponentFixture<ProfileComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ProfileComponent],
      imports: [MaterialModule, RouterTestingModule, HttpClientTestingModule],
      providers: [{
        provide: ProfileService, useValue: {
          getLoggedInProfile: () => <Profile>{
            identity: <Identity>{
              name: "Testuser"
            }
          }
        }
      }]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
