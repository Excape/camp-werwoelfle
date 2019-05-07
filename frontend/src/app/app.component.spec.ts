import {async, TestBed} from '@angular/core/testing';
import {AppComponent} from './app.component';
import {RouterModule} from "@angular/router";
import {APP_BASE_HREF} from "@angular/common";
import {NavigationComponent} from "./shared/navigation/navigation.component";
import {LoginComponent} from "./login/login.component";
import {LobbyComponent} from "./lobby/lobby.component";
import {PlayerListComponent} from "./lobby/player-list/player-list.component";
import {LobbyService} from "./lobby/lobby.service";
import {IsLoggedInGuard} from "./is-logged-in.guard";
import {MaterialModule} from "./material/material.module";
import {FormsModule} from "@angular/forms";
import {LoginScreenGuard} from "./login-screen.guard";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {RouterTestingModule} from "@angular/router/testing";

describe('AppComponent', () => {
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        AppComponent,
        NavigationComponent,
        LoginComponent,
        LobbyComponent,
        PlayerListComponent
      ],
      imports: [
        RouterTestingModule,
        MaterialModule,
        FormsModule,
        RouterModule.forRoot([
          {path: '', redirectTo: '/', pathMatch: 'full'}
        ]),
        HttpClientTestingModule,
      ],
      providers: [{provide: APP_BASE_HREF, useValue: '/'}, LobbyService, IsLoggedInGuard, LoginScreenGuard]
    }).compileComponents();
  }));
  it('should create the app', async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  }));
  it('should render title in a h1 tag', async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    fixture.detectChanges();
    const compiled = fixture.debugElement.nativeElement;
    expect(compiled.querySelector('h1').textContent).toContain('Werwölfle');
  }));
});
