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

  public createGame(name: string) {
    // TODO: replace mock
    let profile = <Profile>{
      name: 'Test'

    };
    return this.httpClient.post(`${this.backendUrl}create?gameName=${name}`, profile)
  }

  public joinGame(name: string) {
    let profile = <Profile>{
      name: 'Albert'

    };
    return this.httpClient.post(`${this.backendUrl}join?gameName=${name}`, profile)
  }
}
