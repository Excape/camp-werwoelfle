import {Component, OnInit} from '@angular/core';
import {Observable} from "rxjs";
import {Game} from "../shared/model/dtos";
import {LobbyService} from "./lobby.service";

@Component({
  selector: 'app-lobby',
  templateUrl: './lobby.component.html',
  styleUrls: ['./lobby.component.scss']
})
export class LobbyComponent implements OnInit {
  games$: Observable<Game[]>;
  joinedGame: Game;
  gameName: string;

  constructor(private lobbyService: LobbyService) {
  }

  ngOnInit() {
    this.fetchGames();
  }

  fetchGames() {
    this.games$ = this.lobbyService.getGames()
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

}
