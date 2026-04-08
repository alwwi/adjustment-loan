import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RatingModelPage } from './rating-model.page';

describe('RatingModelPage', () => {
  let component: RatingModelPage;
  let fixture: ComponentFixture<RatingModelPage>;

  beforeEach(() => {
    fixture = TestBed.createComponent(RatingModelPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
