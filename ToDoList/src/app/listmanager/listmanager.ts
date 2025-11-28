import { Component, signal, Output, EventEmitter, OnInit, inject} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { RouterOutlet } from '@angular/router';
import { ListItem } from '../list-item/list-item';
import { TodoList } from "../todo-list/todo-list";
import { Todo } from '../todo';

@Component({
  selector: 'app-listmanager',
  imports: [TodoList],
  templateUrl: './listmanager.html',
  styleUrl: './listmanager.css',
})
export class Listmanager {
  protected selectedTask = signal<string>('');

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


  protected onTodoSelected(todo: Todo) {
    console.log('Selected todo item in App component:', todo.task);
    this.selectedTask.set(todo.task);
    //this.getFact();
  }

  protected onTodoDeleted(todo_id: number) {
    console.log('Deleted todo item in App component with id:', todo_id);
    this.ToDoList.set(this.ToDoList().filter(todo => todo.id !== todo_id));
  }
  protected onTodoAdded(todo: Todo) {
    if (todo.id==0) {
      todo.id = this.ToDoList().length > 0 ? Math.max(...this.ToDoList().map(t => t.id)) + 1 : 1;
    } else {
      //Update existing todo
      this.ToDoList.set(this.ToDoList().map(t => t.id === todo.id ? todo : t));
    }
    console.log('Added todo item in App component:', todo.task);
    this.ToDoList.set([...this.ToDoList(), todo]);
  }
}
