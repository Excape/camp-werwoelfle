import { Component, OnInit } from '@angular/core';
import {ApiService} from "../shared/api.service";
import {Observable} from "rxjs";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  helloCamp$: Observable<string>;

  constructor(private apiService: ApiService) { }

  ngOnInit() {
    this.helloCamp$ = this.apiService.get("")
  }

}
