import {Component} from '@angular/core';
import {environment} from "../environments/environment";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  name = environment.name;
  pwaActiveMessage = environment.pwaActive ? "with PWA" : "without PWA";

  getEnvSpecificColor(): string {
    return environment.production ? 'red' : 'green';
  }
}
