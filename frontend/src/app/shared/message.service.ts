import { Injectable } from '@angular/core';
import {MqttService} from "ngx-mqtt";

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
}
