import {Component, OnInit} from '@angular/core';
import {ApiService} from "../shared/api.service";
import {Observable} from "rxjs";
import {ProfileService} from "../shared/profile.service";
import {Profile} from "../shared/model/dtos";
import {Router} from "@angular/router";
import {ToastrService} from "ngx-toastr";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  name: string = "";
  password_plain: string = "";
  profileExists: boolean = false;
  helloCamp$: Observable<string>;

  constructor(private apiService: ApiService,
              private profileService: ProfileService,
              private router: Router,
              private toastrService: ToastrService
              )
  { }

  ngOnInit() {
    this.helloCamp$ = this.apiService.get("")
  }

  checkIfProfileExists() {
    this.profileService.getProfile(this.name).subscribe(profile => {
      this.profileExists = true;
    }, error => {
      this.profileExists = false;
      console.log(error)
    })
  }

  login() {
    const profile = this.createProfile();
    this.profileService.login(profile).subscribe(status => {
      this.loginLogic(profile)
    }, error=> {
      this.displayLogin(error);
      this.password_plain = "";
      console.log(error)
    });
  }

  loginLogic(profile: Profile) {
    console.log(profile);
    this.profileService.setLocalProfile(profile);
    this.profileService.updateLoggedInProfile(profile);
    console.log("login successful");
    this.router.navigateByUrl('lobby')
  }

  create() {
    const profile = this.createProfile();
    this.profileService.createProfile(profile).subscribe(status => {
      this.loginLogic(profile)
    }, error=> {
      this.displayCreate(error);
      console.log(error)
    });
  }

  private createProfile(): Profile {
    return {
      name: this.name,
      password_plain: this.password_plain,
    }
  }

  private displayLogin(error) {
    if (error instanceof HttpErrorResponse) {
      switch (error.status) {
        case 400:
        case 404: {
          this.toastrService.error('Username or password is not correct.', 'Login failure');
          break
        }
        case 504: {
          this.toastrService.error('Connection problem with backend.', 'Login failure');
          break
        }
        default: {
          this.toastrService.error('Unknown problem.', 'Login failure');
        }
      }
    }
  }

  private displayCreate(error) {
    if (error instanceof HttpErrorResponse) {
      switch (error.status) {
        case 304: {
          this.toastrService.error('A profile with this username already exists. No profile was created.', 'Sign up failure');
          break
        }
        case 504: {
          this.toastrService.error('Connection problem with backend.', 'Sign up failure');
          break
        }
        default: {
          this.toastrService.error('Unknown problem.', 'Sign up failure');
        }
      }
    }
  }
}
