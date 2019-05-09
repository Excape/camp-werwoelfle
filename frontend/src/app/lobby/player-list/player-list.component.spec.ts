import {async, ComponentFixture, TestBed} from '@angular/core/testing';
import {PlayerListComponent} from './player-list.component';
import {MaterialModule} from "../../material/material.module";
import {Game, Identity, Player, Profile, Role, PlayerState} from "../../shared/model/dtos";

describe('PlayerListComponent', () => {
  let component: PlayerListComponent;
  let fixture: ComponentFixture<PlayerListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PlayerListComponent ], imports:[MaterialModule]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PlayerListComponent);
    component = fixture.componentInstance;
    component.game = <Game>{
      name: "Test",
      players: [<Player>{
        identity: <Identity>{
          name: "Albert"
        },
        role: Role.WEREWOLF,
        state: PlayerState.ALIVE
      }]
    };
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
