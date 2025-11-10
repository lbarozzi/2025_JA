import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-scegli-colore',
  standalone: true,
  imports: [CommonModule],
  templateUrl: 'scegli-colore.html',
  styleUrl: 'scegli-colore.css'
})
export class ScegliColore implements OnInit {
  @Input() coloriInput: string = '';
  @Output() colorescelto = new EventEmitter<string>();

  colori: string[] = [];

  ngOnInit() {
    // Converte la stringa "red green blue" in un array ["red", "green", "blue"]
    console.log('coloriInput:', this.coloriInput);
    if (this.coloriInput) {
      this.colori = this.coloriInput.split(' ').filter(c => c.trim() !== '');
    }
    console.log('colori array:', this.colori);
  }

  selezionaColore(colore: string) {
    this.colorescelto.emit(colore);
  }
}
