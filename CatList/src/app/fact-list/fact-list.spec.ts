import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FactList } from './fact-list';

describe('FactList', () => {
  let component: FactList;
  let fixture: ComponentFixture<FactList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FactList]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FactList);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
