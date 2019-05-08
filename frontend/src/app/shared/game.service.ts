import { Injectable } from '@angular/core';
import {Game, Phases} from "./model/dtos";
import {IMqttMessage} from "ngx-mqtt";
import {MessageService} from "./message.service";
import {Router} from "@angular/router";
import {Subject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class GameService {

  private _currentPhase$: Subject<Phases> = new Subject();

  currentPhase(): Subject<Phases> {
    return this._currentPhase$;
  }

  constructor(private messageService: MessageService,
              private router: Router
  ) { }

  subscribe(game: Game) {
    this.messageService.subscribeToGame(game).subscribe( (message: IMqttMessage) => {
      let payload = message.payload.toString();
      console.log("message " + payload);
      this.handleStart(payload);
      this.handlePhase(payload);
    })

  }

  private handleStart(payload) {
    if (payload.startsWith("START")) {
      this.router.navigateByUrl('content')
    }
  }

  private handlePhase(payload) {
    if (payload.startsWith('PHASE')) {
      switch (payload) {
        case "PHASE_ROLE": {
          this._currentPhase$.next(Phases.ROLE);
          break;
        }
        case "PHASE_DAY": {
          this._currentPhase$.next(Phases.DAY);
          break;
        }
        case "PHASE_WEREWOLF": {
          this._currentPhase$.next(Phases.WEREWOLF);
          break;
        }
        default: {
          console.log("could not match phase");
          break;
        }
      }

    }
  }
}
