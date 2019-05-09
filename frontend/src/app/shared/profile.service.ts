import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable, Subject} from "rxjs";
import {Profile} from "./model/dtos";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})

export class ProfileService {

  profileUrl = 'api/v1/profile/';
  private _loggedInProfile$: Subject<Profile> = new Subject();

  updateLoggedInProfile(profile: Profile) {
    this._loggedInProfile$.next(profile);
  }

  get loggedInProfile(): Subject<Profile> {
    return this._loggedInProfile$;
  }

  constructor(private httpClient: HttpClient, private router: Router) {
  }

  getProfile(username: string): Observable<Profile> {
    return this.httpClient.get<Profile>(this.profileUrl + username);
  }

  setLocalProfile(profile: Profile) {
    sessionStorage.setItem("profile", JSON.stringify(profile));
  }

  getLoggedInProfile(): Profile {
    return JSON.parse(sessionStorage.getItem("profile"));
  }

  login(profile: Profile): Observable<void> {
    return this.httpClient.post<void>(this.profileUrl + "login", profile);
  }

  logout() {
    sessionStorage.removeItem("profile");
    this.updateLoggedInProfile(null);
  }

  createProfile(profile: Profile): Observable<void> {
    return this.httpClient.post<void>(this.profileUrl, profile);
  }

  getCurrentIdentity() {
    return this.getLoggedInProfile().identity
  }
}
