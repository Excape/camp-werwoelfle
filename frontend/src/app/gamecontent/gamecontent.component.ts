import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {Phases, Role} from "../shared/model/dtos";
import {GameService} from "../shared/game.service";

@Component({
  selector: 'app-gamecontent',
  templateUrl: './gamecontent.component.html',
  styleUrls: ['./gamecontent.component.css']
})
export class GamecontentComponent implements OnInit, OnDestroy {

  messages: string[] = [];
  private _subscription: Subscription;
  activePhase : Phases;
  playerRole: Role;

  constructor(private _gameService: GameService) {
  }

  ngOnInit() {
    this._gameService.currentPhase().subscribe(phase => this.activePhase = phase);
    this._gameService.currentRole().subscribe(role => this.playerRole = role);
  }

  ngOnDestroy(): void {
    this._subscription.unsubscribe();
  }

  shouldDisplayRolePhase(): boolean {
    return this.activePhase == Phases.ROLE;
  }

  shouldDisplayDayPhase() {
    return this.activePhase == Phases.DAY;
  }

  shouldDisplayWerewolfPhase() {
    return this.activePhase == Phases.WEREWOLF;
  }

  sendAck() {
    this._gameService.sendAck();
  }
}
