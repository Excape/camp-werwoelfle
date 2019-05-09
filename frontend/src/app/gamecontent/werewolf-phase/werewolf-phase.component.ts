import {Component, OnInit} from '@angular/core';
import {AudioService} from "../../shared/audio.service";

@Component({
  selector: 'app-werewolf-phase',
  templateUrl: './werewolf-phase.component.html',
  styleUrls: ['./werewolf-phase.component.scss']
})
export class WerewolfPhaseComponent implements OnInit {

  constructor(private audioService: AudioService) { }

  ngOnInit() {
    this.audioService.playAudio("../../../assets/sound/wolf-howl.wav");
  }




}
