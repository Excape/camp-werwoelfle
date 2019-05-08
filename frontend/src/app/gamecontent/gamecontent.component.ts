import {Component, OnDestroy, OnInit} from '@angular/core';
import {MessageService} from "../shared/message.service";
import {IMqttMessage} from "ngx-mqtt";
import {Subscription} from "rxjs";
import {Phases, Role} from "../shared/model/dtos";

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

  constructor(private _messageService: MessageService) {
  }

  ngOnInit() {
    this._subscription = this._messageService.subscribe('test', (message: IMqttMessage) => {
      this.messages.push(message.payload.toString());
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
