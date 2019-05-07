import {Component, OnInit} from '@angular/core';
import {ApiService} from "../shared/api.service";
import {Observable} from "rxjs";
import {ProfileService} from "../shared/profile.service";
import {Profile} from "../shared/model/dtos";
import {Router} from "@angular/router";

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

  constructor(private apiService: ApiService,
              private profileService: ProfileService,
              private router: Router
              )
  { }

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

  login() {
    const profile = this.createProfile();
    this.profileService.login(profile).subscribe(status => {
      this.loginLogic(profile)
    }, error=> {
      console.log(error)
    });
  }

  loginLogic(profile: Profile) {
    console.log(profile);
    this.profileService.setLocalProfile(profile);
    console.log("login successful");
    this.router.navigateByUrl('lobby')
  }



  create() {
    const profile = this.createProfile();
    this.profileService.createProfile(profile).subscribe(status => {
      this.loginLogic(profile)
    }, error=> {
      console.log(error)
    });
  }

  private createProfile() {
    return {
      name: this.name,
      password: this.password
    };
  }
}
