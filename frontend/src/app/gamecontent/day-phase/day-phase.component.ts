import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {AudioService} from "../../shared/audio.service";
import {Player, Voting} from "../../shared/model/dtos";

@Component({
  selector: 'day-phase',
  templateUrl: './day-phase.component.html',
  styleUrls: ['./day-phase.component.scss']
})
export class DayPhaseComponent implements OnInit {
  @Input() voting: Voting;
  @Output() electedPlayersEmitter = new EventEmitter<Player[]>();

  constructor(private audioService: AudioService) {
  }

  ngOnInit() {
    this.audioService.playAudio("../../../assets/sound/day-phase.wav");
  }

  electPlayers($event: Player[]) {
    this.electedPlayersEmitter.emit($event)
  }

}
