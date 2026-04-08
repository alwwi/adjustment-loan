import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AddLoanCategoryPage } from './add-loan-category.page';

const routes: Routes = [
  {
    path: '',
    component: AddLoanCategoryPage
  },  {
    path: 'loan-type',
    loadChildren: () => import('./loan-type/loan-type.module').then( m => m.LoanTypePageModule)
  },
  {
    path: 'rating-model',
    loadChildren: () => import('./rating-model/rating-model.module').then( m => m.RatingModelPageModule)
  }

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AddLoanCategoryPageRoutingModule {}
