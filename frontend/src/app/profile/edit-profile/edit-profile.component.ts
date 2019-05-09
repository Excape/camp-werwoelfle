import {Component, Input, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {Profile} from "../../shared/model/dtos";
import {ProfileService} from "../../shared/profile.service";
import {HttpErrorResponse} from "@angular/common/http";
import {ToastrService} from "ngx-toastr";
import {AngularFireStorage, AngularFireStorageReference, AngularFireUploadTask} from "@angular/fire/storage";
import {Task} from "protractor/built/taskScheduler";

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.scss']
})
export class EditProfileComponent implements OnInit {
  task: AngularFireUploadTask;
  ref: AngularFireStorageReference;
  profile: Profile;

  constructor(private router: Router, private  profileService: ProfileService, private toasterService: ToastrService, private afStorage: AngularFireStorage) {

  }

  ngOnInit() {
    this.profile = this.profileService.;
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

  upload(event) {
    const randomId = Math.random().toString(36).substring(2);
    this.ref = this.afStorage.ref(randomId);
    this.task = this.ref.put(event.target.files[0]);
  }

}
