import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AddSyncLoanPage } from './add-sync-loan.page';

describe('AddSyncLoanPage', () => {
  let component: AddSyncLoanPage;
  let fixture: ComponentFixture<AddSyncLoanPage>;

  beforeEach(() => {
    fixture = TestBed.createComponent(AddSyncLoanPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
