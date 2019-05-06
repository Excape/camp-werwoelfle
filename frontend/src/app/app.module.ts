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
import {IMqttServiceOptions, MqttModule} from "ngx-mqtt";
import { GamecontentComponent } from './gamecontent/gamecontent.component';

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
    GamecontentComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot(routes),
    MaterialModule,
    MqttModule.forRoot(MQTT_SERVICE_OPTIONS),
    ServiceWorkerModule.register('ngsw-worker.js', {enabled: environment.production}),
  ],
  providers: [
    IsLoggedInGuard,
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
