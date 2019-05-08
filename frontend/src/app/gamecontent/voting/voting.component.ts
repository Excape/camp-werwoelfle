import { Component, OnInit } from '@angular/core';
import {Player, Voting} from "../../shared/model/dtos";
import {VotingService} from "./voting.service";

@Component({
  selector: 'app-voting',
  templateUrl: './voting.component.html',
  styleUrls: ['./voting.component.scss']
})
export class VotingComponent implements OnInit {
  question: string
  voting: Voting

  constructor(private votingService: VotingService) { }

  ngOnInit() {
    this.voting = this.votingService.getVoting()
    this.question = this.getQuestion()
  }

  getNumberOfCurrentVotedPlayers(): number {
    return this.voting.votees.filter(player=>player.checked).map(player=>player).length
  }

  sendVote() {

  }

  getQuestion(): string {
    return "Who do you want to kill?"
  }
}
