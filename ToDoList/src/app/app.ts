import { Component, signal, Output, EventEmitter, OnInit, inject} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { RouterOutlet } from '@angular/router';
import { ListItem } from './list-item/list-item';
import { TodoList } from "./todo-list/todo-list";
import { Todo } from './todo';
import { Listmanager } from "./listmanager/listmanager";

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, Listmanager]  ,
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('ToDoList');
  protected catfact = signal<string>('');
  protected http=inject(HttpClient);
   protected ngOnInit() {
    console.log('ng OnInit: App component initialized.');
    let myip='https://api.ipify.org?format=json' ;
    this.http.get<{ip:string}>(myip).subscribe({
      next: (data) => {
        console.log('Fetched IP address:', data.ip);
      }
    });

    let catsfacts='https://catfact.ninja/facts?limit=2' ;
    this.http.get<{current_page:Number,data:{fact:string}[]}>(catsfacts).subscribe({
      next: (data) => {
        console.log('Fetched cat facts:', JSON.stringify(data.data));
      }
    });
    this.getFact();
  }

  protected getFact() {
    const catfact='https://catfact.ninja/fact' ;
    this.http.get<{fact:string}>(catfact).subscribe({
      next: (data) => {
        console.log('Fetched single cat fact:', data.fact);
        this.catfact.set(data.fact);
      }
    });
  }
}
