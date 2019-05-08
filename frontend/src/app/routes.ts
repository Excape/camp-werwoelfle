import {Routes} from "@angular/router";
import {LoginComponent} from "./login/login.component";
import {LobbyComponent} from "./lobby/lobby.component";
import {IsLoggedInGuard} from "./is-logged-in.guard";
import {GamecontentComponent} from "./gamecontent/gamecontent.component";
import {LoginScreenGuard} from "./login-screen.guard";
import {VotingComponent} from "./gamecontent/voting/voting.component";
import {ProfileComponent} from "./profile/profile.component";
import {EditProfileComponent} from "./profile/edit-profile/edit-profile.component";

export const routes: Routes = [
  {path: 'login', component: LoginComponent, canActivate: [LoginScreenGuard]},
  {path: 'lobby', component: LobbyComponent, canActivate: [IsLoggedInGuard]},
  {path: 'content', component: GamecontentComponent, canActivate: [IsLoggedInGuard]},
  {path: 'voting', component: VotingComponent},
  {path: 'profile', component: ProfileComponent},
  {path: 'editProfile', component: EditProfileComponent},
  {path: '', redirectTo: '/lobby', pathMatch: 'full'}
];
