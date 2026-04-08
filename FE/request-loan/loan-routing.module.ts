import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { LoanPage } from './loan.page';

const routes: Routes = [
  {
    path: '',
    component: LoanPage
  },
  {
    path: 'loan-list',
    loadChildren: () => import('./loan-list/loan-list.module').then(m => m.LoanListPageModule)
  },
  {
    path: 'early-settlement',
    loadChildren: () => import('./early-settlement/early-settlement.module').then(m => m.EarlySettlementPageModule)
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class LoanPageRoutingModule { }
