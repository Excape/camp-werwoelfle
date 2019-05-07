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
    sessionStorage.getItem("profile")
    return this.httpClient.post<Game>(`${this.backendUrl}create?gameName=${name}`, this.getProfileFromStorage())
  }

  public joinGame(name: string): Observable<Game> {
    return this.httpClient.post<Game>(`${this.backendUrl}join?gameName=${name}`, this.getProfileFromStorage())
  }

  private getProfileFromStorage(): Profile {
    return JSON.parse(sessionStorage.getItem("profile"));
  }
}
