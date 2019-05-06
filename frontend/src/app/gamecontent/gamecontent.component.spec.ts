import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GamecontentComponent } from './gamecontent.component';

describe('GamecontentComponent', () => {
  let component: GamecontentComponent;
  let fixture: ComponentFixture<GamecontentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GamecontentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GamecontentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
