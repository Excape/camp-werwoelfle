import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VotingComponent } from './voting.component';
import {MaterialModule} from "../../material/material.module";

describe('VotingComponent', () => {
  let component: VotingComponent;
  let fixture: ComponentFixture<VotingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VotingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VotingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
