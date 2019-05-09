import {Component, Input, OnInit} from '@angular/core';
import {AudioService} from "../../shared/audio.service";
import {Voting} from "../../shared/model/dtos";

@Component({
  selector: 'day-phase',
  templateUrl: './day-phase.component.html',
  styleUrls: ['./day-phase.component.scss']
})
export class DayPhaseComponent implements OnInit {
  @Input() voting: Voting;

  constructor(private audioService: AudioService) {
  }

  ngOnInit() {
    this.audioService.playAudio("../../../assets/sound/day-phase.wav");
  }

}
