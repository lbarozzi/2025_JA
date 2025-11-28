import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Listmanager } from './listmanager';

describe('Listmanager', () => {
  let component: Listmanager;
  let fixture: ComponentFixture<Listmanager>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Listmanager]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Listmanager);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
