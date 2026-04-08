import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AddVehicleTypePage } from './add-vehicle-type.page';

describe('AddVehicleTypePage', () => {
  let component: AddVehicleTypePage;
  let fixture: ComponentFixture<AddVehicleTypePage>;

  beforeEach(() => {
    fixture = TestBed.createComponent(AddVehicleTypePage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
