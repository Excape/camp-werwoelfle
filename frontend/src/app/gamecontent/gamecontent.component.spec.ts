import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GamecontentComponent } from './gamecontent.component';
import {MessageService} from "../shared/message.service";

describe('GamecontentComponent', () => {
  let component: GamecontentComponent;
  let fixture: ComponentFixture<GamecontentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GamecontentComponent ],
      providers: [{provide: MessageService, useValue: {subscribe: (topic, onNext) => {}}}]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GamecontentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
