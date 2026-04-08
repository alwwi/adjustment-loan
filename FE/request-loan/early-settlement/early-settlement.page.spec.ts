import { ComponentFixture, TestBed } from '@angular/core/testing';
import { EarlySettlementPage } from './early-settlement.page';

describe('EarlySettlementPage', () => {
  let component: EarlySettlementPage;
  let fixture: ComponentFixture<EarlySettlementPage>;

  beforeEach(() => {
    fixture = TestBed.createComponent(EarlySettlementPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
