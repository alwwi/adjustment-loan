import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { RatingModelMatrixPage } from './rating-model-matrix.page';

const routes: Routes = [
  {
    path: '',
    component: RatingModelMatrixPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class RatingModelMatrixPageRoutingModule {}
