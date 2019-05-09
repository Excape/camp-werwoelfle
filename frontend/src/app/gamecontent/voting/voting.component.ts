import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Player, Voting} from "../../shared/model/dtos";

@Component({
  selector: 'app-voting',
  templateUrl: './voting.component.html',
  styleUrls: ['./voting.component.scss']
})
export class VotingComponent implements OnInit {
  question: string;
  @Input() voting: Voting;
  @Output() electedPlayersEmitter = new EventEmitter<Player[]>();

  constructor() {
  }

  ngOnInit() {
    this.question = this.getQuestion()
  }

  getNumberOfCurrentVotedPlayers(): number {
    return this.voting.candidates.filter(player => player.checked).map(player => player).length
  }

  sendVote() {
    this.electedPlayersEmitter.next(this.voting.candidates.filter(candidate => candidate.checked))
  }

  getQuestion(): string {
    return "Who do you want to kill?"
  }
}
