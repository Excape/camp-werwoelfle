import {Component, Input, OnInit} from '@angular/core';
import {Profile} from "../shared/model/dtos";
import {NavigationExtras, Router} from "@angular/router";
import {ProfileService} from "../shared/profile.service";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  profile: Profile;

  constructor(private router: Router, private profileService: ProfileService) {
  }

  ngOnInit() {
    this.profile = this.profileService.getLoggedInProfile();
  }

  editProfile() {
    this.router.navigateByUrl('editProfile');

  }
}
