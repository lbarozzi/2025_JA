import { Component, Input, Output,EventEmitter } from '@angular/core';
import { ListItem } from '../list-item/list-item';

@Component({
  selector: 'app-todo-list',
  imports: [ListItem,],
  templateUrl: './todo-list.html',
  styleUrl: './todo-list.css',
})
export class TodoList {
  @Input() todos!: {id: number, dueDate: string, task: string,
    completed: boolean, priority: string}[];
    @Output() selected = new EventEmitter<{id: number, dueDate: string, task: string,
    completed: boolean, priority: string}>();
    @Output() deleted = new EventEmitter<number>();
    @Output() adding = new EventEmitter<{id: number, dueDate: string, task: string,
    completed: boolean, priority: string}>()

  protected onTodoDeleted(todo_id: number) {
    console.log('Deleted todo item in App component with id:', todo_id);
    //this.todos.set(this.todos.filter(todo => todo.id !== todo_id));
    this.deleted.emit(todo_id);
  }
  protected onTodoAdd(todo: {task: string, dueDate: string, priority: string}) {
    console.log('Added todo item in App component:', todo.task);
    //this.ToDoList.set([...this.ToDoList(), todo]);

    this.adding.emit( { id:0, completed:false, ...todo});
  }
  protected onTodoSelected(todo: {id: number, dueDate: string, task: string,
    completed: boolean, priority: string}) {
    console.log('Selected todo item in App component:', todo.task);
    this.selected.emit(todo);
  }

}
