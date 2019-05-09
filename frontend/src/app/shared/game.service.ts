import {Injectable} from '@angular/core';
import {Game, OutboundMessage, Phases, Player, Role} from "./model/dtos";
import {IMqttMessage} from "ngx-mqtt";
import {MessageService} from "./message.service";
import {Router} from "@angular/router";
import {Observable, Subject, Subscription} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {ProfileService} from "./profile.service";

@Injectable({
  providedIn: 'root'
})
export class GameService {

  private _currentPhase$: Subject<Phases> = new Subject();
  private _currentRole$: Subject<Role> = new Subject();
  private _dyingPlayers$: Subject<Player[]> = new Subject();
  private _getAck$: Subject<void> = new Subject();
  profileUrl = 'api/v1/game';
  private game: Game;
  currentPlayer: Player;
  private _gameSubscription: Subscription;


  currentPhase(): Subject<Phases> {
    return this._currentPhase$;
  }

  currentRole(): Subject<Role> {
    return this._currentRole$;
  }

  dyingPlayers(): Subject<Player[]> {
    return this._dyingPlayers$;
  }

  getAck(): Subject<void> {
    return this._getAck$;
  }

  constructor(private messageService: MessageService,
              private profileService: ProfileService,
              private router: Router,
              private httpClient: HttpClient
  ) {
  }

  subscribe(game: Game) {
    let currentIdentity = this.profileService.getCurrentIdentity();
    this.currentPlayer = game.players.find(player => player.identity.name === currentIdentity.name);
    this._gameSubscription = this.messageService.subscribeToGame(game).subscribe((message: IMqttMessage) => {
      this.game = game;
      let payload = message.payload.toString();
      console.log("message " + payload);
      this.handleStart(payload);
      this.handlePhase(payload);
    });
    this.messageService.subscribeToPlayer(game, this.currentPlayer).subscribe(message => {
        let payload = JSON.parse(message.payload.toString());
        switch (OutboundMessage[<string>payload.type]) {
          case OutboundMessage.ROLE:
            console.log(`message ${payload.type}: ${payload.role}`);
            this.currentPlayer.role = Role[<string>payload.role];
            this._currentRole$.next(this.currentPlayer.role);
            break;
          case OutboundMessage.VOTING:
            console.log(`message ${payload.type}: ${payload.voting}`);
            break;
          case OutboundMessage.WAKE_UP:
            console.log(`message ${payload.type}: ${payload.role}`);
            let dyingPlayers = payload.dyingPlayers;
            this._dyingPlayers$.next(dyingPlayers);
            break;
          case OutboundMessage.GET_ACK:
            console.log(`message ${payload.type}`);
            this._getAck$.next();
            break;
          default:
            console.log(`Unknown message ${payload.type}`)
        }

      }
    );
  }

  sendAck() {
    this.messageService.publishAck(this.game, this.currentPlayer)
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
        case "PHASE_NIGHTFALL": {
          this._currentPhase$.next(Phases.NIGHT_FALL);
          break;
        }
        case "PHASE_WAKEUP": {
          this._currentPhase$.next(Phases.WAKEUP);
          break;
        }
        default: {
          console.log("could not match phase: " + payload);
          break;
        }
      }

    }
  }

  getPlayerRoleFor(playerName: string, gameName: string): Observable<Role> {
    let roleUrl = `${this.profileUrl}/${gameName}/${playerName}/role`;
    return this.httpClient.get<Role>(roleUrl);
  }

  getGame(): Game {
    return this.game;
  }

  getPlayerFor(playerName: string): Observable<Player> {
    let gameName = this.getGame().name;
    let playerUrl = `${this.profileUrl}/${gameName}/${playerName}`;

    return this.httpClient.get<Player>(playerUrl);
  }

  unsubscribe(game: Game) {
    if (this._gameSubscription) {
      this._gameSubscription.unsubscribe();
    }
  }
}
