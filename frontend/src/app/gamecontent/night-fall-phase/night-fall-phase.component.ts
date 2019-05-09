import {Component, OnInit} from '@angular/core';
import {AudioService} from "../../shared/audio.service";

@Component({
  selector: 'app-night-fall-phase',
  templateUrl: './night-fall-phase.component.html',
  styleUrls: ['./night-fall-phase.component.scss']
})
export class NightFallPhaseComponent implements OnInit {


  constructor(private audioService: AudioService) { }

  ngOnInit() {
    this.audioService.playAudio("../../../assets/sound/lullaby.wav");
  }


}
