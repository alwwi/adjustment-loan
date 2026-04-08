import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AddTransferEvidencePage } from './add-transfer-evidence.page';

describe('AddTransferEvidencePage', () => {
  let component: AddTransferEvidencePage;
  let fixture: ComponentFixture<AddTransferEvidencePage>;

  beforeEach(() => {
    fixture = TestBed.createComponent(AddTransferEvidencePage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
