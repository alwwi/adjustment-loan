import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { VehicleTypePage } from './vehicle-type.page';

const routes: Routes = [
  {
    path: '',
    component: VehicleTypePage
  },  {
    path: 'add-vehicle-type',
    loadChildren: () => import('./add-vehicle-type/add-vehicle-type.module').then( m => m.AddVehicleTypePageModule)
  }

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class VehicleTypePageRoutingModule {}
