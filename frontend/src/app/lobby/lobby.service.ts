import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Game} from "../shared/model/dtos";

@Injectable({
  providedIn: 'root'
})
export class LobbyService {
  private backendUrl = "api/v1/";

  constructor(private httpClient: HttpClient) { }

   public getGames(): Observable<Game[]> {
      return this.httpClient.get<Game[]>(this.backendUrl);
    }
}
