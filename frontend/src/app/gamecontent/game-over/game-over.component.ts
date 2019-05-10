import {Component, Input, OnInit} from '@angular/core';
import {Role} from "../../shared/model/dtos";
import {Router} from "@angular/router";

@Component({
  selector: 'app-game-over',
  templateUrl: './game-over.component.html',
  styleUrls: ['./game-over.component.scss']
})
export class GameOverComponent implements OnInit {
  @Input() roleThatWon: Role;

  constructor(private router: Router) {}

  ngOnInit() {
  }

  werewolvesWon() {
    return this.roleThatWon === Role.WEREWOLF;
  }

  parseRole() {
    switch (this.roleThatWon) {
      case Role.WEREWOLF:
        return "Werewolves";
      case Role.VILLAGER:
        return "Villagers";
    }
  }

  exitGame() {
    this.router.navigateByUrl("lobby")
  }
}
