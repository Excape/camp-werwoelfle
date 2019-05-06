import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  backendUrl = 'api/v1';

  constructor(private httpClient: HttpClient) { }

  public get(endpoint: string): Observable<any> {
    return this.httpClient.get(this.backendUrl + endpoint);
  }
}
