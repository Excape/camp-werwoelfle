import {Component, OnDestroy, OnInit} from '@angular/core';
import {MessageService} from "../shared/message.service";
import {IMqttMessage} from "ngx-mqtt";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-gamecontent',
  templateUrl: './gamecontent.component.html',
  styleUrls: ['./gamecontent.component.css']
})
export class GamecontentComponent implements OnInit, OnDestroy {

  messages: string[] = [];
  private _subscription: Subscription;

  constructor(private _messageService: MessageService) {
  }

  ngOnInit() {
    this._subscription = this._messageService.subscribe('HELLO', (message: IMqttMessage) => {
      this.messages.push(message.payload.toString());
    });
  }

  ngOnDestroy(): void {
    this._subscription.unsubscribe();
  }

}
