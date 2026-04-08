import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AddLoanCategoryPage } from './add-loan-category.page';

describe('AddLoanCategoryPage', () => {
  let component: AddLoanCategoryPage;
  let fixture: ComponentFixture<AddLoanCategoryPage>;

  beforeEach(() => {
    fixture = TestBed.createComponent(AddLoanCategoryPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
