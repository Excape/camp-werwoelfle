import {Injectable} from '@angular/core';
import {IMqttMessage, MqttService} from "ngx-mqtt";
import {Game, Player, Vote} from "./model/dtos";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  constructor(private _mqttService: MqttService) {
  }

  public subscribe(topic: string, onNext: any) {
    return this._mqttService.observe(topic).subscribe(onNext)
  }

  public publish(topic: string, message: string) {
    this._mqttService.unsafePublish(topic, message);
  }

  public publishAck(game: Game, player: Player) {
    let message = JSON.stringify({"type": "ACK"});
    let topic = `${game.name}/${player.identity.name}/ACK`;
    this.publish(topic, message)
  }

  public publishVote(game: Game, player: Player, vote: Vote){
    let message = JSON.stringify({"type": "VOTE", vote: vote});
    let topic = `${game.name}/${player.identity.name}/VOTE`;
    this.publish(topic, message)
  }

  public subscribeToPlayer(game: Game, player: Player): Observable<IMqttMessage> {
    let topic = `${game.name}/${player.identity.name}`;
    return this._mqttService.observe(topic);
  }

  public subscribeToGame(game: Game): Observable<IMqttMessage> {
    let topic = `${game.name}`;
    return this._mqttService.observe(topic);
  }
}
