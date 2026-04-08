import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AddVehicleTypePage } from './add-vehicle-type.page';

const routes: Routes = [
  {
    path: '',
    component: AddVehicleTypePage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AddVehicleTypePageRoutingModule {}
