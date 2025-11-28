import { HttpClient } from '@angular/common/http';
import { Component, OnInit, OnChanges , SimpleChanges, inject, signal, Output, Input, SimpleChange } from '@angular/core';

@Component({
  selector: 'app-fact-list',
  imports: [],
  templateUrl: './fact-list.html',
  styleUrl: './fact-list.css',
  })
export class FactList implements OnInit, OnChanges{
  protected http =inject(HttpClient);
  protected facts = signal<string[]>([]);
  protected readonly catfact = 'https://catfact.ninja/fact';
  @Input() numberOfFacts: number = 5;

  ngOnInit(): void {
    this.loadFacts();
  }

   ngOnChanges(changes: SimpleChanges): void {
    if (changes['numberOfFacts'] && !changes['numberOfFacts'].firstChange) {
      this.loadFacts();
    }
  }

  protected loadFacts(): void {
    this.facts.set([]);
    for (let i=0; i < this.numberOfFacts; i++) {
      this.getFact();

    }

  }

  protected getFact(): void{
    this.http.get<{fact:string}>(this.catfact).subscribe({
        next: (data) => {
          this.appendFact(data.fact);
        }
      });
  }
  protected appendFact(fact: string): void {
    this.facts.set([...this.facts(), fact]);
  }

  protected onFactDeleted(fact: string): void {
    this.facts.set(this.facts().filter(f => f !== fact));
    this.getFact();
  }

}
