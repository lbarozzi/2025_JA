import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { FactList } from "./fact-list/fact-list";

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, FactList],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('CatList');
  protected numberOfFacts = signal(5);
}
