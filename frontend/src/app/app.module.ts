import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {RouterModule} from "@angular/router";
import {routes} from "./routes";
import {LoginComponent} from "./login/login.component";
import {MaterialModule} from "./material/material.module";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {ServiceWorkerModule} from '@angular/service-worker';
import {environment} from '../environments/environment';
import {HttpClientModule} from "@angular/common/http";
import {LobbyComponent} from './lobby/lobby.component';
import {NavigationComponent} from './shared/navigation/navigation.component';
import {IsLoggedInGuard} from "./is-logged-in.guard";
import {FormsModule} from "@angular/forms";
import {PlayerListComponent} from './lobby/player-list/player-list.component';
import {IMqttServiceOptions, MqttModule} from "ngx-mqtt";
import {GameContentComponent} from './gamecontent/game-content.component';
import {LoginScreenGuard} from "./login-screen.guard";
import {VotingComponent} from './gamecontent/voting/voting.component';
import {ToastrModule} from "ngx-toastr";
import {RolePhaseComponent} from './gamecontent/role-phase/role-phase.component';
import {DayPhaseComponent} from './gamecontent/day-phase/day-phase.component';
import {WerewolfPhaseComponent} from './gamecontent/werewolf-phase/werewolf-phase.component';
import {ProfileComponent} from './profile/profile.component';
import {EditProfileComponent} from './profile/edit-profile/edit-profile.component';
import {MatCardModule} from "@angular/material";
import { GameOverComponent } from './gamecontent/game-over/game-over.component';
import {MatFileUploadModule} from "angular-material-fileupload";
import {WakeUpPhaseComponent} from './gamecontent/wake-up-phase/wake-up-phase.component';
import {SpinnerComponent} from './shared/spinner/spinner.component';
import {NightFallPhaseComponent} from './gamecontent/night-fall-phase/night-fall-phase.component';
import {ExecutionPhaseComponent} from "./gamecontent/execution-phase/execution-phase.component";
// import * as firebase from "firebase";
// import Firestore = firebase.firestore.Firestore;
// import {AngularFireStorage, AngularFireStorageModule} from "@angular/fire/storage";
// import {AngularFireModule} from "@angular/fire";

export const MQTT_SERVICE_OPTIONS: IMqttServiceOptions = {
  hostname: 'm24.cloudmqtt.com',
  protocol: 'wss',
  port: 32829,
  username: 'dbqfeten',
  password: 'zLTr3XfKwb_g'
};

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    LobbyComponent,
    NavigationComponent,
    PlayerListComponent,
    VotingComponent,
    GameContentComponent,
    RolePhaseComponent,
    DayPhaseComponent,
    WerewolfPhaseComponent,
    GameContentComponent,
    ProfileComponent,
    EditProfileComponent,
    GameOverComponent,
    WakeUpPhaseComponent,
    SpinnerComponent,
    NightFallPhaseComponent,
    ExecutionPhaseComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot(routes),
    MaterialModule,
    MatCardModule,
    MqttModule.forRoot(MQTT_SERVICE_OPTIONS),
    ToastrModule.forRoot(),
    ServiceWorkerModule.register('ngsw-worker.js', {enabled: environment.production}),
    MatFileUploadModule
    // AngularFireModule.initializeApp(environment.firebaseConfig),
    // AngularFireStorageModule
  ],
  providers: [
    IsLoggedInGuard,
    LoginScreenGuard,
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
