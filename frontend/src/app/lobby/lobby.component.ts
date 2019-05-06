import { Component, OnInit } from '@angular/core';
import {ApiService} from "../shared/api.service";
import {Observable} from "rxjs";
import {Game} from "../shared/model/dtos";

@Component({
  selector: 'app-lobby',
  templateUrl: './lobby.component.html',
  styleUrls: ['./lobby.component.css']
})
export class LobbyComponent implements OnInit {
  games$: Observable<Game[]>

  constructor(private apiService: ApiService) {}

  ngOnInit() {
    this.games$ = this.apiService.get("")
  }

}
