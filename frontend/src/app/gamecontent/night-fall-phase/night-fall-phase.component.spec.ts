import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {NightFallPhaseComponent} from './night-fall-phase.component';

describe('NightFallPhaseComponent', () => {
  let component: NightFallPhaseComponent;
  let fixture: ComponentFixture<NightFallPhaseComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NightFallPhaseComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NightFallPhaseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
