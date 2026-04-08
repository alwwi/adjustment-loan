import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RatingModelMatrixPage } from './rating-model-matrix.page';

describe('RatingModelMatrixPage', () => {
  let component: RatingModelMatrixPage;
  let fixture: ComponentFixture<RatingModelMatrixPage>;

  beforeEach(() => {
    fixture = TestBed.createComponent(RatingModelMatrixPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
