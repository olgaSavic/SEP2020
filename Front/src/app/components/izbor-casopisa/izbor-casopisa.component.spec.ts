import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IzborCasopisaComponent } from './izbor-casopisa.component';

describe('IzborCasopisaComponent', () => {
  let component: IzborCasopisaComponent;
  let fixture: ComponentFixture<IzborCasopisaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IzborCasopisaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IzborCasopisaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
