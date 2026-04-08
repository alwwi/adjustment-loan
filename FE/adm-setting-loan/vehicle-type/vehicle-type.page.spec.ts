import { ComponentFixture, TestBed } from '@angular/core/testing';
import { VehicleTypePage } from './vehicle-type.page';

describe('VehicleTypePage', () => {
  let component: VehicleTypePage;
  let fixture: ComponentFixture<VehicleTypePage>;

  beforeEach(() => {
    fixture = TestBed.createComponent(VehicleTypePage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
