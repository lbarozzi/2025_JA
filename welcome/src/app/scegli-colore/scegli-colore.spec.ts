import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ScegliColore } from './scegli-colore';

describe('ScegliColore', () => {
  let component: ScegliColore;
  let fixture: ComponentFixture<ScegliColore>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ScegliColore]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ScegliColore);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
