import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Player, PlayerState, Role} from "../../shared/model/dtos";
import {GameService} from "../../shared/game.service";
import {ProfileService} from "../../shared/profile.service";

@Component({
  selector: 'app-game-over',
  templateUrl: './game-over.component.html',
  styleUrls: ['./game-over.component.scss']
})
export class GameOverComponent implements OnInit {
  private player: Player;

  @Input() roleThatWon: Role;
  @Output() ackEmitter = new EventEmitter();


  ngOnInit() {
  }

  confirm() {
    this.ackEmitter.emit()
  }

  isWerewolf() {
    return this.roleThatWon === Role.WEREWOLF;
  }


  currentPlayerHasWon(): Boolean {
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
