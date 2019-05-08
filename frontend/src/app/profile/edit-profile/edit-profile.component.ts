import {Component, Input, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {Profile} from "../../shared/model/dtos";
import {ProfileService} from "../../shared/profile.service";
import {HttpErrorResponse} from "@angular/common/http";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.scss']
})
export class EditProfileComponent implements OnInit {

  profile: Profile;

  constructor(private router: Router, private  profileService: ProfileService, private toasterService: ToastrService) {
  }

  ngOnInit() {
    this.profile = this.profileService.getLoggedInProfile();
  }

  saveProfile() {
    if (!this.checkIfProfileExists()) {
      this.profileService.updateLoggedInProfile(this.profile);
      this.profileService.setLocalProfile(this.profile);
      this.router.navigateByUrl('profile');
    }
  }

  checkIfProfileExists(): boolean {
    this.profileService.getProfile(this.profile.identity.name).subscribe(
      profile => {
        return !!profile;
      }, error => {
        return false;
      });
    return false;
  }
}
