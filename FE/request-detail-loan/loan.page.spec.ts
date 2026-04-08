import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LoanPage } from './loan.page';

describe('LoanPage', () => {
  let component: LoanPage;
  let fixture: ComponentFixture<LoanPage>;

  beforeEach(() => {
    fixture = TestBed.createComponent(LoanPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
