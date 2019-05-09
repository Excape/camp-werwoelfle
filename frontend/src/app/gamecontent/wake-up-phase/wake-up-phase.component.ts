import {Component, EventEmitter, Input, OnChanges, OnInit, Output} from '@angular/core';
import {AudioService} from "../../shared/audio.service";
import {Player} from "../../shared/model/dtos";

@Component({
  selector: 'app-wake-up-phase',
  templateUrl: './wake-up-phase.component.html',
  styleUrls: ['./wake-up-phase.component.scss']
})
export class WakeUpPhaseComponent implements OnInit, OnChanges {

  @Input() deadVillagers: Player[];
  @Output() ackEmitter = new EventEmitter();

  confirmed = false;
  subtitle: string = "";

  constructor(private audioService: AudioService) { }

  ngOnInit() {
    this.audioService.playAudio("../../../assets/sound/daybreak.wav");
  }

  ngOnChanges() {
    if (this.deadVillagers.length == 0) {
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
