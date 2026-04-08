import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { RatingModelPage } from './rating-model.page';

const routes: Routes = [
  {
    path: '',
    component: RatingModelPage
  },  {
    path: 'rating-model-matrix',
    loadChildren: () => import('./rating-model-matrix/rating-model-matrix.module').then( m => m.RatingModelMatrixPageModule)
  }

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class RatingModelPageRoutingModule {}
