import { ComponentFixture, TestBed } from '@angular/core/testing';
import { SyncLoanPage } from './sync-loan.page';

describe('SyncLoanPage', () => {
  let component: SyncLoanPage;
  let fixture: ComponentFixture<SyncLoanPage>;

  beforeEach(() => {
    fixture = TestBed.createComponent(SyncLoanPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
