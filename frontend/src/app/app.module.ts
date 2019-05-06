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
import { LobbyComponent } from './lobby/lobby.component';
import { NavigationComponent } from './shared/navigation/navigation.component';
import {IsLoggedInGuard} from "./is-logged-in.guard";
import {FormsModule} from "@angular/forms";
import { PlayerListComponent } from './lobby/player-list/player-list.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    LobbyComponent,
    NavigationComponent,
    PlayerListComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot(routes),
    MaterialModule,
    ServiceWorkerModule.register('ngsw-worker.js', {enabled: environment.production})
  ],
  providers: [
    IsLoggedInGuard
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
