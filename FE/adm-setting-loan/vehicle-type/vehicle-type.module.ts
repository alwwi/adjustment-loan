import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { VehicleTypePageRoutingModule } from './vehicle-type-routing.module';

import { VehicleTypePage } from './vehicle-type.page';
import { GlobalComponentsModule } from 'src/app/components/components.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    VehicleTypePageRoutingModule,
    GlobalComponentsModule
  ],
  declarations: [VehicleTypePage]
})
export class VehicleTypePageModule { }
