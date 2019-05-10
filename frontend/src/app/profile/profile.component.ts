import {Component, Input, OnInit} from '@angular/core';
import {Picture, Profile} from "../shared/model/dtos";
import {NavigationExtras, Router} from "@angular/router";
import {ProfileService} from "../shared/profile.service";
import {PictureService} from "../picture.service";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  profile: Profile;
  picture: Picture;

  constructor(private router: Router,
              private profileService: ProfileService,
              private pictureService: PictureService
  ) {
  }

  ngOnInit() {
    this.profile = this.profileService.getLoggedInProfile();
    this.pictureService.loadPictureFor(this.profile.identity.name).subscribe(
      picture => {
        this.picture = picture;
      }
    )
  }

  editProfile() {
    this.router.navigateByUrl('editProfile');

  }
}
