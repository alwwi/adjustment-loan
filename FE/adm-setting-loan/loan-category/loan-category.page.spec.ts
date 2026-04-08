import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LoanCategoryPage } from './loan-category.page';

describe('LoanCategoryPage', () => {
  let component: LoanCategoryPage;
  let fixture: ComponentFixture<LoanCategoryPage>;

  beforeEach(() => {
    fixture = TestBed.createComponent(LoanCategoryPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
