import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LobbyComponent } from './lobby.component';
import {Component, Input, OnInit} from "@angular/core";
import {Game} from "../shared/model/dtos";
import {Form, FormsModule} from "@angular/forms";
import {MaterialModule} from "../material/material.module";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {NoopAnimationsModule} from "@angular/platform-browser/animations";

@Component({
  selector: 'app-player-list',
  template: 'mock',
})
export class PlayerListMockComponent {
  @Input() game: Game
}

describe('LobbyComponent', () => {
  let component: LobbyComponent;
  let fixture: ComponentFixture<LobbyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LobbyComponent, PlayerListMockComponent ],
      imports: [FormsModule, MaterialModule, HttpClientTestingModule, NoopAnimationsModule]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LobbyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
