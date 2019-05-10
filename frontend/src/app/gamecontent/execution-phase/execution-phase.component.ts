import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Player} from "../../shared/model/dtos";
import {GameService} from "../../shared/game.service";

@Component({
  selector: 'app-execution-phase',
  templateUrl: './execution-phase.component.html',
  styleUrls: ['./execution-phase.component.scss']
})
export class ExecutionPhaseComponent implements OnInit {

  @Input() deadVillagers: Player[];
  @Output() ackEmitter = new EventEmitter();

  confirmed = false;
  needsAck = false;
  subtitle: string = "The following player was executed: ";

  constructor(private gameService: GameService) { }

  ngOnInit() {
    this.gameService.dyingPlayers().subscribe(next => this.needsAck = true)
  }


  confirm() {
    this.confirmed = true;
    this.ackEmitter.emit()
  }
}
