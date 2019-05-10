import {Component, OnDestroy, OnInit} from '@angular/core';
import {Phases, Player, Role, Voting} from "../shared/model/dtos";
import {GameService} from "../shared/game.service";

@Component({
  selector: 'app-game-content',
  templateUrl: './game-content.component.html',
  styleUrls: ['./game-content.component.css']
})
export class GameContentComponent implements OnInit, OnDestroy {

  activePhase: Phases;
  playerRole: Role;
  dyingPlayers: Player[];
  voting: Voting;
  winningRole: Role;

  constructor(private _gameService: GameService) {
  }

  ngOnInit() {
    this._gameService.currentPhase().subscribe(phase => {
      this.activePhase = phase;
      this.dyingPlayers = [];
      this.voting = null;
    });
    this._gameService.currentRole().subscribe(role => this.playerRole = role);
    this._gameService.dyingPlayers().subscribe(dyingPlayers => this.dyingPlayers = dyingPlayers);
    this._gameService.voting().subscribe(voting => this.voting = voting);
    this._gameService.winningRole().subscribe(winningRole => this.winningRole = winningRole);
  }

  ngOnDestroy(): void {
    this._gameService.currentPhase().unsubscribe();
    this._gameService.currentRole().unsubscribe();
    this._gameService.dyingPlayers().unsubscribe();
    this._gameService.voting().unsubscribe();
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

  shouldDisplayGameOver() {
    return this.activePhase == Phases.GAME_OVER;
  }

  shouldDisplayExecutionPhase() {
    return this.activePhase == Phases.EXECUTION;
  }

  sendAck() {
    this._gameService.sendAck();
  }

  electPlayers($event: Player[]) {
    this._gameService.sendVote($event)
  }
}
