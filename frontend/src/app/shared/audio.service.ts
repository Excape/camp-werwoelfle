import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AudioService {

  constructor() { }

  playAudio(url) {
    let audio = new Audio();
    audio.src = url;
    audio.load();
    audio.play();
  }
}
