import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Game, Profile} from "../shared/model/dtos";

@Injectable({
  providedIn: 'root'
})
export class LobbyService {
  private backendUrl = "api/v1/";

  constructor(private httpClient: HttpClient) {
  }

  public getGames(): Observable<Game[]> {
    return this.httpClient.get<Game[]>(this.backendUrl);
  }

  public createGame(name: string): Observable<Game> {
    // TODO: replace mock
    let profile = <Profile>{
      name: 'Test',
      password: '1234'
    };
    return this.httpClient.post<Game>(`${this.backendUrl}create?gameName=${name}`, profile)
  }

  public joinGame(name: string): Observable<Game> {
    let profile = <Profile>{
      name: 'Albert',
      password: '1234'
    };
    return this.httpClient.post<Game>(`${this.backendUrl}join?gameName=${name}`, profile)
  }
}
