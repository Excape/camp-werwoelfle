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
  gameName: string;

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
    this.lobbyService.createGame(this.gameName).subscribe(game => {
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

  startGame(game: Game) {
    this.lobbyService.startGame(game).subscribe(value =>
      console.log(value));
  }

  alreadyJoined(game: Game) {
    const currentProfile: Profile = JSON.parse(sessionStorage.getItem("profile"));
    return this.flatMap(game => game.players, this.games)
      .find((player: Player) => player.identity.name === currentProfile.identity.name);
  }

  concat = (x, y) =>
    x.concat(y);
  flatMap = (f, xs) =>
    xs.map(f).reduce(this.concat, [])

}
