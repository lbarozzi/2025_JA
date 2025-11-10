import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ScegliColore } from './scegli-colore/scegli-colore';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, ScegliColore],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('welcome');

  logColor(colore: string) {
    console.log('logColore selezionato:', colore);
  }
}
