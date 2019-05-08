import { Injectable } from '@angular/core';
import {ProfileService} from "../../shared/profile.service";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Game, Player, Profile, Role, State, Vote} from "../../shared/model/dtos";

@Injectable({
  providedIn: 'root'
})
export class VotingService {
  private backendUrl = "api/v1"
  voter: Profile;

  constructor(private httpClient: HttpClient, private profileService: ProfileService) {
    this.voter = <Profile>{
        name: "sdiegas",
        password: "1234"
    }
    this.profileService.loggedInProfile.subscribe(loggedInProfile => {
      this.voter = loggedInProfile
    })
  }

  public submitVote(players: Player[]) {
    vote: [<Vote>{
      voteOf: <Player>{
        profile: this.voter
      },
      voteFor: players
    }]
  }

  public getOptions(): Player[]{
    return (
      [<Player>{
        profile: <Profile>{
          name: "Albert",
          password: "1234"
        },
        state: State.AWAKE,
        role: Role.VILLAGER
      }, <Player>{
        profile: <Profile>{
          name: "Mario",
          password: "1234"
        },
        state: State.AWAKE,
        role: Role.VILLAGER
      }, <Player>{
        profile: <Profile>{
          name: "Stefanie",
          password: "1234"
        },
        state: State.AWAKE,
        role: Role.WEREWOLF
      }, <Player>{
        profile: <Profile>{
          name: "sdiegas",
          password: "1234"
        },
        state: State.AWAKE,
        role: Role.VILLAGER
      }, <Player>{
        profile: <Profile>{
          name: "Robin",
          password: "1234"
        },
        state: State.AWAKE,
        role: Role.WEREWOLF
      }
      ]
    ).filter(player => player.profile.name != this.voter.name).map(player => player)
  }
}
