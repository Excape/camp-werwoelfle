import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {EditProfileComponent} from './edit-profile.component';
import {MatCardModule} from "@angular/material";
import {MaterialModule} from "../../material/material.module";
import {FormsModule} from "@angular/forms";
import {RouterTestingModule} from "@angular/router/testing";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {ToastrModule} from "ngx-toastr";
import {ProfileService} from "../../shared/profile.service";
import {Identity, Profile} from "../../shared/model/dtos";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";

describe('EditProfileComponent', () => {
  let component: EditProfileComponent;
  let fixture: ComponentFixture<EditProfileComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [EditProfileComponent],
      imports: [MaterialModule,
        FormsModule,
        RouterTestingModule,
        HttpClientTestingModule,
        BrowserAnimationsModule,
        ToastrModule.forRoot()
      ],
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
    fixture = TestBed.createComponent(EditProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
