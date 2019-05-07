import { Injectable } from '@angular/core';
import {IMqttMessage, MqttService} from "ngx-mqtt";
import {Game, Profile} from "./model/dtos";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  constructor(private _mqttService: MqttService) {
  }

  public subscribe(topic: string, onNext: any){
    return this._mqttService.observe(topic).subscribe(onNext)
  }

  public publish(topic: string, message: string) {
    this._mqttService.unsafePublish(topic, message);
  }

  public subscribePlayer(game: Game, profile: Profile): Observable<IMqttMessage> {
    let topic = `${game.name}/${profile.id}`;
    return this._mqttService.observe(topic);
  }

  public subscribeToGame(game: Game): Observable<IMqttMessage> {
    let topic = `${game.name}`;
    return this._mqttService.observe(topic);
  }
}
