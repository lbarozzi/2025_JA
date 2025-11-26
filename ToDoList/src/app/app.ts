import { Component, signal, Output, EventEmitter, OnInit, inject} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { RouterOutlet } from '@angular/router';
import { ListItem } from './list-item/list-item';
import { TodoList } from "./todo-list/todo-list";

@Component({
  selector: 'app-root',
  imports: [RouterOutlet,  TodoList],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected http=inject(HttpClient);
  protected readonly title = signal('ToDoList');
  protected selectedTask = signal<string>('');
  protected catfact = signal<string>('');
  protected ToDoList = signal<{id: number, dueDate: string, task: string,
    completed: boolean, priority: string}[]>
  ([
    {id: 1 , dueDate: '2024-07-01', task: 'Buy groceries',
      completed: false, priority: 'High'},
    {id: 2 , dueDate: '2024-07-03', task: 'Finish project report',
      completed: false, priority: 'Medium'},
    {id: 3 , dueDate: '2024-07-05', task: 'Call the bank',
      completed: true, priority: 'Low'},
    {id: 4 , dueDate: '2024-07-07', task: 'Schedule doctor appointment',
      completed: false, priority: 'High'},
    {id: 5 , dueDate: '2024-07-10', task: 'Plan weekend trip',
      completed: false, priority: 'Medium'},
    {id: 6 , dueDate: '2024-07-12', task: 'Clean the house',
      completed: true, priority: 'Low'}
  ]);

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
  protected onTodoSelected(todo: {id: number, dueDate: string, task: string,
    completed: boolean, priority: string}) {
    console.log('Selected todo item in App component:', todo.task);
    this.selectedTask.set(todo.task);
    this.getFact();
  }

  protected onTodoDeleted(todo_id: number) {
    console.log('Deleted todo item in App component with id:', todo_id);
    this.ToDoList.set(this.ToDoList().filter(todo => todo.id !== todo_id));
  }
  protected onTodoAdded(todo: {id: number, dueDate: string, task: string,
    completed: boolean, priority: string}) {
    console.log('Added todo item in App component:', todo.task);
    this.ToDoList.set([...this.ToDoList(), todo]);
  }
}
