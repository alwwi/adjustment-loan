import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { LoanCategoryPage } from './loan-category.page';

const routes: Routes = [
  {
    path: '',
    component: LoanCategoryPage
  },  {
    path: 'add-loan-category',
    loadChildren: () => import('./add-loan-category/add-loan-category.module').then( m => m.AddLoanCategoryPageModule)
  }

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class LoanCategoryPageRoutingModule {}
