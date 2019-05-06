import {Routes} from "@angular/router";
import {LoginComponent} from "./login/login.component";
import {LobbyComponent} from "./lobby/lobby.component";
import {IsLoggedInGuard} from "./is-logged-in.guard";
import {GamecontentComponent} from "./gamecontent/gamecontent.component";

export const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'lobby', component: LobbyComponent, canActivate: [IsLoggedInGuard]},
  {path: 'content', component: GamecontentComponent},
  {path: '', redirectTo: '/lobby', pathMatch: 'full'}
];
