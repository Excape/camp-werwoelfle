import { TestBed } from '@angular/core/testing';

import { MessageService } from './message.service';
import {MqttService} from "ngx-mqtt";

class MockMqttService {}

describe('MessageService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [{provide: MqttService, useClass: MockMqttService}]
    });
  });

  it('should be created', () => {
    const service: MessageService = TestBed.get(MessageService);
    expect(service).toBeTruthy();
  });
});
