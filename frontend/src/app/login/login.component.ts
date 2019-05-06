import {Component, Input, OnInit} from '@angular/core';
import {ApiService} from "../shared/api.service";
import {Observable} from "rxjs";
import {ProfileService} from "../shared/profile.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  username: string = "";
  profileExists: boolean = false;
  helloCamp$: Observable<string>;

  constructor(private apiService: ApiService, private profileService: ProfileService) { }

  ngOnInit() {
    this.helloCamp$ = this.apiService.get("")
  }

  checkIfProfileExists() {
    this.profileService.getProfile(this.username).subscribe(profile => {
      this.profileExists =  profile.name && profile.name.length > 0;
    }, error => {
      console.log(error)
    })
  }
}
