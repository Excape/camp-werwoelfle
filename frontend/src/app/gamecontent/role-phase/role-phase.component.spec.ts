import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RolePhaseComponent } from './role-phase.component';
import {MaterialModule} from "../../material/material.module";

describe('RolePhaseComponent', () => {
  let component: RolePhaseComponent;
  let fixture: ComponentFixture<RolePhaseComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RolePhaseComponent ],
      imports: [MaterialModule]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RolePhaseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
