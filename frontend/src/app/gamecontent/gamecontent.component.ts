import {Component, OnDestroy, OnInit} from '@angular/core';
import {MessageService} from "../shared/message.service";
import {IMqttMessage} from "ngx-mqtt";
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
    this._gameService.currentPhase().subscribe(phase => {
      this.activePhase = phase;
    });
    this.playerRole = Role.VILLAGER;
    this.activePhase = Phases.DAY;
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
}
