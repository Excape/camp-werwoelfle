import {Component, OnDestroy, OnInit} from '@angular/core';
import {Phases, Player, Role, Voting} from "../shared/model/dtos";
import {GameService} from "../shared/game.service";

@Component({
  selector: 'app-game-content',
  templateUrl: './game-content.component.html',
  styleUrls: ['./game-content.component.css']
})
export class GameContentComponent implements OnInit, OnDestroy {

  messages: string[] = [];
  activePhase : Phases;
  playerRole: Role;
  dyingPlayers: Player[];
  voting: Voting;

  constructor(private _gameService: GameService) {
  }

  ngOnInit() {
    this._gameService.currentPhase().subscribe(phase => this.activePhase = phase);
    this._gameService.currentRole().subscribe(role => this.playerRole = role);
    this._gameService.dyingPlayers().subscribe(dyingPlayers => this.dyingPlayers = dyingPlayers);
    this._gameService.voting().subscribe(voting => this.voting = voting)
  }

  ngOnDestroy(): void {
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

  shouldDisplayNightfallPhase() {
    return this.activePhase == Phases.NIGHT_FALL;
  }

  shouldDisplayWakeUpPhase() {
    return this.activePhase == Phases.WAKEUP;
  }

  sendAck() {
    this._gameService.sendAck();
  }

  electPlayers($event: Player[]) {
    this._gameService.sendVote($event)
  }
}
