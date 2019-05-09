import {GameService} from "./game.service";
import {Game, Player, PlayerState, Role, Vote} from "./model/dtos";
import {Observable, of, Subscription} from "rxjs";
import {IMqttMessage} from "ngx-mqtt";
import {MessageService} from "./message.service";

class messageServiceMock extends MessageService {
  constructor() {
    super(null)
  }

  public subscribe(topic: string, onNext: any) {
    return new Subscription()
  }

  public publish(topic: string, message: string) {
  }

  public publishAck(game: Game, player: Player) {
  }

  public publishVote(game: Game, player: Player, vote: Vote) {
  }

  public subscribeToPlayer(game: Game, player: Player): Observable<IMqttMessage> {
    return of(null)
  }

  public subscribeToGame(game: Game): Observable<IMqttMessage> {
    return of(null)
  }
}

const profileServiceMock = {}

describe('GameService', () => {
  it('Should correctly parse enum', () => {
    const spy: MessageService = jasmine.createSpyObj('MessageService', ['publishVote']);
    const s = new GameService(spy, null, null, null);
    s.sendVote([<Player>{
      checked: true,
      role: Role.VILLAGER,
      identity: {
        name: ""
      },
      playerState: PlayerState.ALIVE
    }])

    expect(spy.publishVote).toHaveBeenCalled()

  })

  it('Should correctly parse enum', () => {
    const spy: MessageService = jasmine.createSpyObj('MessageService', ['publishAck']);
    const s = new GameService(spy, null, null, null);
    s.currentPlayer = <Player>{
      checked: true,
      role: Role.VILLAGER,
      identity: {
        name: ""
      },
      state: PlayerState.ALIVE
    }
    s.sendAck();

    expect(spy.publishAck).toHaveBeenCalled()

  })
});
