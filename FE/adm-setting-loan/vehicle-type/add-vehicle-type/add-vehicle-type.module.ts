import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { AddVehicleTypePageRoutingModule } from './add-vehicle-type-routing.module';

import { AddVehicleTypePage } from './add-vehicle-type.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    AddVehicleTypePageRoutingModule
  ],
  declarations: [AddVehicleTypePage]
})
export class AddVehicleTypePageModule {}
