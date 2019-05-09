import {Component, EventEmitter, Input, OnChanges, OnInit, Output} from '@angular/core';
import {Role} from "../../shared/model/dtos";

@Component({
  selector: 'role-phase',
  templateUrl: './role-phase.component.html',
  styleUrls: ['./role-phase.component.scss']
})
export class RolePhaseComponent implements OnInit, OnChanges {
  roleExplanation: String = "";
  roleTitle: String = "";
  roleSubtitle: String = "";
  imgUrl: String = "";

  @Input() playerRole: Role;

  @Output() ackEmitter = new EventEmitter();

  constructor() { }

  ngOnInit() {

  }

  ngOnChanges() {
    this.updateView()
  }

  confirm() {
    this.ackEmitter.emit()
  }

  updateView() {
    switch (this.playerRole) {
      case Role.VILLAGER: {
        this.roleTitle = "Villager";
        this.roleSubtitle = "Good guy";
        this.roleExplanation = "A villager can do nothing. You win when all werewolves die.";
        this.imgUrl = "../../../assets/gamecontent/villager.png";
        break
      }
      case Role.WEREWOLF: {
        this.roleTitle = "Werewolf";
        this.roleSubtitle = "Bad guy";
        this.roleExplanation = "Kill all villagers.";
        this.imgUrl = "../../../assets/gamecontent/werewolf.png";
        break
      }
    }
  }

}
