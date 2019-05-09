import {Injectable} from '@angular/core';
import {Game, Phases, Player, Role} from "./model/dtos";
import {IMqttMessage} from "ngx-mqtt";
import {MessageService} from "./message.service";
import {Router} from "@angular/router";
import {Observable, Subject} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {ProfileService} from "./profile.service";

@Injectable({
  providedIn: 'root'
})
export class GameService {

  private _currentPhase$: Subject<Phases> = new Subject();
  private _currentRole$: Subject<Role> = new Subject();
  profileUrl = 'api/v1/game';
  private game: Game;
  currentPlayer: Player;


  currentPhase(): Subject<Phases> {
    return this._currentPhase$;
  }

  currentRole(): Subject<Role> {
    return this._currentRole$;
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
    this.messageService.subscribeToGame(game).subscribe((message: IMqttMessage) => {
      this.game = game;
      let payload = message.payload.toString();
      console.log("message " + payload);
      this.handleStart(payload);
      this.handlePhase(payload);
    });
    this.messageService.subscribeToPlayer(game, this.currentPlayer).subscribe(message => {
        let payload = JSON.parse(message.payload.toString());
        switch (payload.type) {
          case "ROLE":
            console.log(`message ${payload.type}: ${payload.role}`);
            this.currentPlayer.role = Role[<string>payload.role];
            this._currentRole$.next(this.currentPlayer.role);
            break;
          case "VOTING":
            console.log(`message ${payload.type}: ${payload.voting}`);

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
        default: {
          console.log("could not match phase");
          break;
        }
      }

    }
  }

  getPlayerRoleFor(playerName: string, gameName: string): Observable<Role> {
    let roleUrl = `${this.profileUrl}/${gameName}/${playerName}/role`;
    return this.httpClient.get<Role>(roleUrl);
  }

  getGame(): Game{
    return this.game;
  }

  getPlayerFor(playerName: string) : Observable<Player>{
    let gameName = this.getGame().name;
    let playerUrl = `${this.profileUrl}/${gameName}/${playerName}`;

   return this.httpClient.get<Player>(playerUrl);
  }
}
