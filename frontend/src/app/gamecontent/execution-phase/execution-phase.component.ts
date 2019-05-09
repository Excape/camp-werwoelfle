import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Player} from "../../shared/model/dtos";

@Component({
  selector: 'app-execution-phase',
  templateUrl: './execution-phase.component.html',
  styleUrls: ['./execution-phase.component.scss']
})
export class ExecutionPhaseComponent implements OnInit {

  @Input() deadVillagers: Player[];
  @Output() ackEmitter = new EventEmitter();

  confirmed = false;
  subtitle: string = "The following player was executed: ";

  constructor() { }

  ngOnInit() {
  }


  confirm() {
    this.confirmed = true;
    this.ackEmitter.emit()
  }
}
