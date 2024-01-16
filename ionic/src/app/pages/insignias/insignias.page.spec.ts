import { ComponentFixture, TestBed } from '@angular/core/testing';
import { InsigniasPage } from './insignias.page';

describe('InsigniasPage', () => {
  let component: InsigniasPage;
  let fixture: ComponentFixture<InsigniasPage>;

  beforeEach(async(() => {
    fixture = TestBed.createComponent(InsigniasPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
