import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Profile} from "./model/dtos";

@Injectable({
  providedIn: 'root'
})

export class ProfileService {

  profileUrl = 'api/v1/profile/'

  constructor(private httpClient: HttpClient) { }

  getProfile(username: string): Observable<Profile> {
    return this.httpClient.get<Profile>(this.profileUrl  + username);
  }

  saveProfile(profile: Profile): Observable<Profile> {
    return this.httpClient.post<Profile>(this.profileUrl, profile, {});
  }
}
