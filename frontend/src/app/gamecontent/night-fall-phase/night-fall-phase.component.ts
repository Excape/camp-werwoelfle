import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {AudioService} from "../../shared/audio.service";
import {GameService} from "../../shared/game.service";

@Component({
  selector: 'app-night-fall-phase',
  templateUrl: './night-fall-phase.component.html',
  styleUrls: ['./night-fall-phase.component.scss']
})
export class NightFallPhaseComponent implements OnInit {

  @Output() ackEmitter = new EventEmitter();

  confirmed = false;
  needsAck = false;

  constructor(private audioService: AudioService, private gameService: GameService) { }

  ngOnInit() {
    this.audioService.playAudio("../../../assets/sound/lullaby.wav");
    this.gameService.getAck().subscribe(() => {
      this.needsAck = true;
    })
  }

  confirm() {
    this.confirmed = true;
    this.ackEmitter.emit()
  }


}
