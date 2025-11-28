import { Component, Input, Output, EventEmitter, ChangeDetectionStrategy } from '@angular/core';
import { Todo } from '../todo';
@Component({
  selector: 'app-list-item',
  imports: [],
  templateUrl: './list-item.html',
  styleUrl: './list-item.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ListItem {
  @Input() todo!: Todo;
  @Output() selected = new EventEmitter<Todo>();
  @Output() deleted = new EventEmitter<number>();
  @Output() adding = new EventEmitter<Todo>();
  OnSelect() {
    console.log('Selected todo item:', this.todo.task);
    this.selected.emit(this.todo);
  }
  onDelete() {
    console.log('Deleted todo item with id:', this.todo.id);
    this.deleted.emit(this.todo.id);
  }
  onAdd(todo: { dueDate: string, task: string,priority: string}) {
    console.log('Adding todo item:', this.todo.task);
    this.adding.emit(this.todo);
  }
}
