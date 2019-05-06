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

  private fetchGames() {
    this.games$ = this.lobbyService.getGames()
  }

  createGame() {
    this.lobbyService.createGame(this.gameName).subscribe(r => this.fetchGames())
  }

  joinGame(game: Game) {
    this.joinedGame = game
    this.lobbyService.joinGame(game.name).subscribe(r => this.fetchGames())
  }

  startGame(game: Game) {

  }

}
