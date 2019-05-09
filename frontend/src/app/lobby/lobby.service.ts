import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Game, Profile} from "../shared/model/dtos";
import {ProfileService} from "../shared/profile.service";

@Injectable({
  providedIn: 'root'
})
export class LobbyService {
  private backendUrl = "api/v1/";

  constructor(private httpClient: HttpClient, private profileService: ProfileService) {
  }

  public getGames(): Observable<Game[]> {
    return this.httpClient.get<Game[]>(this.backendUrl);
  }

  public createGame(name: string): Observable<Game> {
    sessionStorage.getItem("profile");
    let url = `${this.backendUrl}create?gameName=${name}`;
    return this.httpClient.post<Game>(url, this.profileService.getLoggedInProfile())
  }

  public joinGame(name: string): Observable<Game> {
    return this.httpClient.post<Game>(`${this.backendUrl}join?gameName=${name}`, this.profileService.getLoggedInProfile())
  }

  public startGame(game: Game): Observable<Game> {
    return this.httpClient.get<Game>(`${this.backendUrl}start?gameName=${game.name}`);
  }

  leaveGame(name: string) {
    return this.httpClient.post<Game>(`${this.backendUrl}leave?gameName=${name}`, this.profileService.getLoggedInProfile())
  }
}
