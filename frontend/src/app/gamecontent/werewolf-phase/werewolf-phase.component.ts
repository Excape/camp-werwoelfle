import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {AudioService} from "../../shared/audio.service";
import {Player, Voting} from "../../shared/model/dtos";

@Component({
  selector: 'app-werewolf-phase',
  templateUrl: './werewolf-phase.component.html',
  styleUrls: ['./werewolf-phase.component.scss']
})
export class WerewolfPhaseComponent implements OnInit {
  @Input() voting: Voting;
  @Output() electedPlayersEmitter = new EventEmitter<Player[]>();

  constructor(private audioService: AudioService) { }

  ngOnInit() {
    this.audioService.playAudio("../../../assets/sound/wolf-howl.wav");
  }

  electPlayers($event: Player[]) {
    this.electedPlayersEmitter.emit($event)
  }
}
