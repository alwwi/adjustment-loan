import { ComponentFixture, TestBed } from '@angular/core/testing';
import { TransferEvidencePage } from './transfer-evidence.page';

describe('TransferEvidencePage', () => {
  let component: TransferEvidencePage;
  let fixture: ComponentFixture<TransferEvidencePage>;

  beforeEach(() => {
    fixture = TestBed.createComponent(TransferEvidencePage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
