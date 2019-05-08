import {Injectable} from '@angular/core';
import {ProfileService} from "../../shared/profile.service";
import {HttpClient} from "@angular/common/http";
import {Identity, Player, Role, State, Vote, Voting} from "../../shared/model/dtos";

@Injectable({
  providedIn: 'root'
})
export class VotingService {
  private backendUrl = "api/v1"
  voter: Player;

  constructor(private httpClient: HttpClient, private profileService: ProfileService) {
    this.voter = <Player>{
      identity: <Identity>{
        name: "sdiegas"
      },
    }
    this.profileService.loggedInProfile.subscribe(loggedInProfile => {
      this.voter = <Player> {
        identity: loggedInProfile.identity,
        role: Role.WEREWOLF,
        state: State.AWAKE
      }
    })
  }

  public submitVote(players: Player[]) {
    vote: [<Vote>{
      voteOf: this.voter,
      voteFor: players
    }]
  }

  public getVoting(): Voting {
    var voting = <Voting>{
      voters: [<Player>{
        identity: <Identity>{
          name: "Albert"
        },
        state: State.AWAKE,
        role: Role.VILLAGER
      }, <Player>{
        identity: <Identity>{
          name: "Mario"
        },
        state: State.AWAKE,
        role: Role.VILLAGER
      }, <Player>{
        identity: <Identity>{
          name: "Stefanie"
        },
        state: State.AWAKE,
        role: Role.WEREWOLF
      }, <Player>{
        identity: <Identity>{
          name: "sdiegas"
        },
        state: State.AWAKE,
        role: Role.VILLAGER
      }, <Player>{
        identity: <Identity>{
          name: "Robin"
        },
        state: State.AWAKE,
        role: Role.WEREWOLF
      }
      ],
      votees: [<Player>{
        identity: <Identity>{
          name: "Albert"
        },
        state: State.AWAKE,
        role: Role.VILLAGER
      }, <Player>{
        identity: <Identity>{
          name: "Mario"
        },
        state: State.AWAKE,
        role: Role.VILLAGER
      }, <Player>{
        identity: <Identity>{
          name: "Stefanie"
        },
        state: State.AWAKE,
        role: Role.WEREWOLF
      }, <Player>{
        identity: <Identity>{
          name: "sdiegas"
        },
        state: State.AWAKE,
        role: Role.VILLAGER
      }, <Player>{
        identity: <Identity>{
          name: "Robin"
        },
        state: State.AWAKE,
        role: Role.WEREWOLF
      }
      ],
      numberOfVotesPerVoter: 1
    }
    voting.votees = voting.votees
      .filter(player => player.identity.name != this.voter.identity.name)
      .map(player => player)
    return voting
  }
}
