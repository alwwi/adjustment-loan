import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LoanTypePage } from './loan-type.page';

describe('LoanTypePage', () => {
  let component: LoanTypePage;
  let fixture: ComponentFixture<LoanTypePage>;

  beforeEach(() => {
    fixture = TestBed.createComponent(LoanTypePage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
