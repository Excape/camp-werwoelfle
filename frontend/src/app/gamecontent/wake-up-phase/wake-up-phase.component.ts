import {Component, EventEmitter, Input, OnChanges, OnInit, Output} from '@angular/core';
import {AudioService} from "../../shared/audio.service";
import {Player} from "../../shared/model/dtos";
import {GameService} from "../../shared/game.service";

@Component({
  selector: 'app-wake-up-phase',
  templateUrl: './wake-up-phase.component.html',
  styleUrls: ['./wake-up-phase.component.scss']
})
export class WakeUpPhaseComponent implements OnInit, OnChanges {

  @Input() deadVillagers: Player[];
  @Output() ackEmitter = new EventEmitter();

  confirmed = false;
  needsAck = false;
  subtitle: string = "";

  constructor(private audioService: AudioService, private gameService: GameService) { }

  ngOnInit() {
    this.audioService.playAudio("../../../assets/sound/daybreak.wav");
    this.gameService.dyingPlayers().subscribe(next => this.needsAck = true);
  }

  ngOnChanges() {
    if (this.deadVillagers == null || this.deadVillagers.length == 0) {
      this.subtitle = "No players died during the night."
    } else {
      this.subtitle = "The following players died during the night: "
    }
  }

  confirm() {
    this.confirmed = true;
    this.ackEmitter.emit()
  }
}
