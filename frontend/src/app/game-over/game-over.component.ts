import {Component, OnInit} from '@angular/core';
import {Player, Role, State} from "../shared/model/dtos";
import {GameService} from "../shared/game.service";
import {ProfileService} from "../shared/profile.service";

@Component({
  selector: 'app-game-over',
  templateUrl: './game-over.component.html',
  styleUrls: ['./game-over.component.scss']
})
export class GameOverComponent implements OnInit {
  private player: Player;
  private roleThatWon: Role;
  private roleThatLost: Role;

  constructor(private _gameService: GameService,
              private _profileService: ProfileService) {
  }

  ngOnInit() {
    this.roleThatWon = Role.WEREWOLF;
    let userName = this._profileService.getLoggedInProfile().identity.name;
    // this._gameService.getPlayerFor(userName).subscribe(
    //   player => {
    //     this.player = player;
    //   }

    this.player = {
      identity: {
        name: 'alina'
      },
      state: State.AWAKE,
      role: Role.VILLAGER,
      checked: false
    };

    this.assignWonLostRole();
  }

  isWerewolf() {
    return this.player.role === Role.WEREWOLF;
  }


  private assignWonLostRole() {
    if (this.roleThatWon === Role.WEREWOLF) {
      this.roleThatLost = Role.VILLAGER;
    } else {
      this.roleThatWon = Role.VILLAGER;
      this.roleThatLost = Role.WEREWOLF;
    }
  }

  hasWon(): Boolean {
    return this.player.role === this.roleThatWon;
  };

  parseRole() {
    switch (this.roleThatWon) {
      case Role.WEREWOLF:
        return "Werewolves";
      case Role.VILLAGER:
        return "Villagers";
    }
  }
}
