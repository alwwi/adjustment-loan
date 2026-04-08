import { ComponentFixture, TestBed } from '@angular/core/testing';
import { TransferEvidenceDetailPage } from './transfer-evidence-detail.page';

describe('TransferEvidenceDetailPage', () => {
  let component: TransferEvidenceDetailPage;
  let fixture: ComponentFixture<TransferEvidenceDetailPage>;

  beforeEach(() => {
    fixture = TestBed.createComponent(TransferEvidenceDetailPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
