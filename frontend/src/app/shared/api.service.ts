import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable, of} from "rxjs";
import {Game} from "./model/dtos";

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  backendUrl = 'api/v1';
  gamesMock: Game[] = [<Game>{
    name: "Test2"
  }, <Game>{
    name: "Test3"
  }]

  constructor(private httpClient: HttpClient) { }

/*  public get(endpoint: string): Observable<any> {
    return this.httpClient.get(this.backendUrl + endpoint);
  }*/

  public get(endpoint: string): Observable<any> {
    return of(this.gamesMock)
  }
}
