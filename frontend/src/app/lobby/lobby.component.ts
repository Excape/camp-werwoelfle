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
  games$: Observable<Game[]>

  constructor(private lobbyService: LobbyService) {}

  ngOnInit() {
    this.games$ = this.lobbyService.getGames()
  }

}
