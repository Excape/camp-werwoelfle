import { Component, OnInit } from '@angular/core';
import {AudioService} from "../../shared/audio.service";

@Component({
  selector: 'app-wake-up-phase',
  templateUrl: './wake-up-phase.component.html',
  styleUrls: ['./wake-up-phase.component.scss']
})
export class WakeUpPhaseComponent implements OnInit {

  constructor(private audioService: AudioService) { }

  ngOnInit() {
    this.audioService.playAudio("../../../assets/sound/daybreak.wav");
  }

}
