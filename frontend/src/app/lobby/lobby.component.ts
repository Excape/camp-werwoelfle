import {Component, OnInit} from '@angular/core';
import {Game, Player, Profile} from "../shared/model/dtos";
import {LobbyService} from "./lobby.service";
import {MessageService} from "../shared/message.service";
import {ProfileService} from "../shared/profile.service";
import {GameService} from "../shared/game.service";

@Component({
  selector: 'app-lobby',
  templateUrl: './lobby.component.html',
  styleUrls: ['./lobby.component.scss']
})
export class LobbyComponent implements OnInit {
  games: Game[];
  joinedGame: Game;
  newGameName: string;

  MIN_PLAYER_PER_GAME: number = 1;

  constructor(private lobbyService: LobbyService,
              private messageService: MessageService,
              private profileService: ProfileService,
              private gameService: GameService) {
  }

  ngOnInit() {
    this.fetchGames();
  }

  fetchGames() {
    this.lobbyService.getGames().subscribe(games => this.games = games.filter(game => !game.isRunning))
  }

  createGame() {
    this.lobbyService.createGame(this.newGameName).subscribe(game => {
      this.joinedGame = game;
      this.fetchGames();
      this.subscribeTo(game)
    })
  }

  joinGame(game: Game) {
    this.lobbyService.joinGame(game.name).subscribe(game => {
      this.joinedGame = game;
      this.fetchGames();
      this.subscribeTo(game);
    });
  }
  private subscribeTo(game: Game) {
    this.gameService.subscribe(game);
  }

  private unsubscribeTo(game: Game) {
    this.gameService.unsubscribe(game);
  }

  startGame(game: Game) {
    this.lobbyService.startGame(game).subscribe(value =>
      console.log(value));
  }

  alreadyJoined(game: Game) {
    const name = this.profileService.getCurrentIdentity().name;
    let joinedCount = 0;
    this.games.forEach( game => {
      joinedCount += game.players.filter(player => {
        return player.identity.name.localeCompare(name) == 0;
      }).length;
    });

    return joinedCount > 0;
    //return this.flatMap(game => game.players, this.games)
    //  .find((player: Player) => player.identity.name === name);
  }

  concat = (x, y) =>
    x.concat(y);
  flatMap = (f, xs) =>
    xs.map(f).reduce(this.concat, [])

  isStartGameDisabled(game: Game): Boolean {
    return game.players.length < this.MIN_PLAYER_PER_GAME;
  }

  isJoinDisabled(game: Game) {
    return this.alreadyJoined(game)
  }

  leave(game: Game) {
    this.lobbyService.leaveGame(game.name).subscribe(  returnedGame => {
      this.joinedGame = null;
      this.fetchGames();
      this.unsubscribeTo(returnedGame);
    });

  }

  isLeaveDisabled(game: Game) {
    return !this.alreadyJoined(game)
  }

  createGameDisabled() {
    let nameNotSet = !this.newGameName || this.newGameName.length == 0;
    let nameAlreadyTaken = !this.newGameName || this.games.filter(game => {
      return game.name == this.newGameName
    }).length > 0;
    let hasAlreadyJoinedGame = this.joinedGame != null
    return nameNotSet || nameAlreadyTaken || hasAlreadyJoinedGame;
  }
}
