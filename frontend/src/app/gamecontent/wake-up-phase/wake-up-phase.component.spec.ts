import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WakeUpPhaseComponent } from './wake-up-phase.component';

describe('WakeUpPhaseComponent', () => {
  let component: WakeUpPhaseComponent;
  let fixture: ComponentFixture<WakeUpPhaseComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WakeUpPhaseComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WakeUpPhaseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
