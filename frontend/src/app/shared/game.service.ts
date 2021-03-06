import {Injectable} from '@angular/core';
import {Game, OutboundMessage, Phases, Player, Role, Vote, Voting} from "./model/dtos";
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
  private _currentPlayer$: Subject<Player> = new Subject();
  private _dyingPlayers$: Subject<Player[]> = new Subject();
  private _getAck$: Subject<void> = new Subject();
  private _voting$: Subject<Voting> = new Subject();
  private _winningRole$: Subject<Role> = new Subject();
  profileUrl = 'api/v1/game';
  currentPlayer: Player;
  private game: Game;
  private _gameSubscription: Subscription;


  currentPhase(): Subject<Phases> {
    return this._currentPhase$;
  }

  getCurrentPlayer(): Subject<Player> {
    return this._currentPlayer$;
  }

  dyingPlayers(): Subject<Player[]> {
    return this._dyingPlayers$;
  }

  getAck(): Subject<void> {
    return this._getAck$;
  }

  voting(): Subject<Voting> {
    return this._voting$;
  }

  winningRole(): Subject<Role> {
    return this._winningRole$;
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
            this._currentPlayer$.next(this.currentPlayer);
            break;
          case OutboundMessage.VOTING:
            console.log(`message ${payload.type}: ${payload.voting}`);
            this._voting$.next(<Voting>payload.voting);
            break;
          case OutboundMessage.DEAD_PLAYERS:
            console.log(`message ${payload.type}: ${payload.dyingPlayers}`);
            this._dyingPlayers$.next(payload.dyingPlayers);
            break;
          case OutboundMessage.GET_ACK:
            console.log(`message ${payload.type}`);
            this._getAck$.next();
            break;
          case OutboundMessage.GAME_OVER:
            console.log(`message ${payload.winningRole}`);
            this._winningRole$.next(payload.winningRole);
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

  sendVote($event: Player[]) {
    const vote = <Vote>{
      voteFor: $event,
      voteOf: this.currentPlayer
    };
    this.messageService.publishVote(this.game, this.currentPlayer, vote)
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
        case "PHASE_GAME_OVER": {
          this._currentPhase$.next(Phases.GAME_OVER);
          break;
        }
        case "PHASE_EXECUTION": {
          this._currentPhase$.next(Phases.EXECUTION);
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
