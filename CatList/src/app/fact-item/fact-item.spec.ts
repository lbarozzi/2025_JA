import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FactItem } from './fact-item';

describe('FactItem', () => {
  let component: FactItem;
  let fixture: ComponentFixture<FactItem>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FactItem]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FactItem);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
