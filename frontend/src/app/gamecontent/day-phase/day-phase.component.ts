import { Component, OnInit } from '@angular/core';
import {AudioService} from "../../shared/audio.service";

@Component({
  selector: 'day-phase',
  templateUrl: './day-phase.component.html',
  styleUrls: ['./day-phase.component.scss']
})
export class DayPhaseComponent implements OnInit {

  constructor(private audioService: AudioService) { }

  ngOnInit() {
    this.audioService.playAudio("../../../assets/sound/day-phase.wav");
  }

}
