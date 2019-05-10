import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Picture} from "./shared/model/dtos";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class PictureService {

  profileUrl = 'api/v1/profile';

  constructor(private _httpClient:HttpClient) { }


  loadPictureFor(name: string):Observable<Picture> {
    return this._httpClient.get<Picture>(`${this.profileUrl}/picture/${name}`);
  }
}
