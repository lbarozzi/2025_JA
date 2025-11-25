import { Component, Input, Output, EventEmitter, ChangeDetectionStrategy } from '@angular/core';

@Component({
  selector: 'app-list-item',
  imports: [],
  templateUrl: './list-item.html',
  styleUrl: './list-item.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ListItem {
  @Input() todo!: {id: number, dueDate: string, task: string,
    completed: boolean, priority: string};
  @Output() selected = new EventEmitter<{id: number, dueDate: string, task: string,
    completed: boolean, priority: string}>();
  @Output() deleted = new EventEmitter<number>();
  @Output() adding = new EventEmitter<{id: number, dueDate: string, task: string,
    completed: boolean, priority: string}>();
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
