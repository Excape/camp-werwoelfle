import {Component, Input, OnInit} from '@angular/core';
import {AudioService} from "../../shared/audio.service";
import {Player} from "../../shared/model/dtos";

@Component({
  selector: 'app-wake-up-phase',
  templateUrl: './wake-up-phase.component.html',
  styleUrls: ['./wake-up-phase.component.scss']
})
export class WakeUpPhaseComponent implements OnInit {

  @Input() deadVillagers: Player[];

  constructor(private audioService: AudioService) { }

  ngOnInit() {
    this.audioService.playAudio("../../../assets/sound/daybreak.wav");
  }

}
