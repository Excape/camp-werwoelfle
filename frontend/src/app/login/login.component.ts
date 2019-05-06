import {Component, Input, OnInit} from '@angular/core';
import {ApiService} from "../shared/api.service";
import {Observable} from "rxjs";
import {ProfileService} from "../shared/profile.service";
import {Profile} from "../shared/model/dtos";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  name: string = "";
  password: string = "";
  profileExists: boolean = false;
  helloCamp$: Observable<string>;

  constructor(private apiService: ApiService, private profileService: ProfileService) { }

  ngOnInit() {
    this.helloCamp$ = this.apiService.get("")
  }

  checkIfProfileExists() {
    this.profileService.getProfile(this.name).subscribe(profile => {
      this.profileExists =  profile.name && profile.name.length > 0;
    }, error => {
      console.log(error)
    })
  }

  save() {
    console.log('saving..')
    const profile: Profile = {
      name: this.name,
      password: this.password
    };
    this.profileService.saveProfile(profile).subscribe( profile => {
      console.log(profile)
    }, error=> {
      console.log(error)
    });
  }
}
