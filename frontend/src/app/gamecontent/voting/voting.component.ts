import { Component, OnInit } from '@angular/core';
import {Player} from "../../shared/model/dtos";
import {VotingService} from "./voting.service";

@Component({
  selector: 'app-voting',
  templateUrl: './voting.component.html',
  styleUrls: ['./voting.component.scss']
})
export class VotingComponent implements OnInit {
  question: string
  votees: Player[]
  numberOfVotes: number

  constructor(private votingService: VotingService) { }

  ngOnInit() {
    this.votees = this.votingService.getOptions()
    this.numberOfVotes = 1
    this.question = this.getQuestion()
  }

  getNumberOfCurrentVotedPlayers(): number {
    return this.votees.filter(player=>player.checked).map(player=>player).length
  }

  sendVote() {

  }

  getQuestion(): string {
    return "Who do you want to kill?"
  }
}
