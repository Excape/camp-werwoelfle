import {Component, OnInit} from '@angular/core';
import {Observable} from "rxjs";
import {Game, Profile} from "../shared/model/dtos";
import {LobbyService} from "./lobby.service";

@Component({
  selector: 'app-lobby',
  templateUrl: './lobby.component.html',
  styleUrls: ['./lobby.component.scss']
})
export class LobbyComponent implements OnInit {
  games: Game[];
  joinedGame: Game;
  gameName: string;

  constructor(private lobbyService: LobbyService) {
  }

  ngOnInit() {
    this.fetchGames();
  }

  fetchGames() {
    this.lobbyService.getGames().subscribe(games => this.games = games)
  }

  createGame() {
    this.lobbyService.createGame(this.gameName).subscribe(game => {
      this.joinedGame = game
      this.fetchGames()
    })
  }

  joinGame(game: Game) {
    this.lobbyService.joinGame(game.name).subscribe(game => {
      this.joinedGame = game
      this.fetchGames()
    })
  }

  startGame(game: Game) {

  }

  alreadyJoined(game: Game) {
    const currentProfile = JSON.parse(sessionStorage.getItem("profile"));
    return this.flatMap(game => game.players, this.games).find(player => player.profile.name === currentProfile.name);
  }

  concat = (x, y) =>
    x.concat(y);

  flatMap = (f, xs) =>
    xs.map(f).reduce(this.concat, [])

}
