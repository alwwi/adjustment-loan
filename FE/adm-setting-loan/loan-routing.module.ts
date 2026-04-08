import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { LoanPage } from './loan.page';

const routes: Routes = [
  {
    path: '',
    component: LoanPage
  },
  {
    path: 'loan-category',
    loadChildren: () => import('./loan-category/loan-category.module').then(m => m.LoanCategoryPageModule)
  },
  {
    path: 'sync-loan',
    loadChildren: () => import('./sync-loan/sync-loan.module').then(m => m.SyncLoanPageModule)
  },
  {
    path: 'vehicle-type',
    loadChildren: () => import('./vehicle-type/vehicle-type.module').then(m => m.VehicleTypePageModule)
  },
  {
    path: 'transfer-evidence',
    loadChildren: () => import('./transfer-evidence/transfer-evidence.module').then(m => m.TransferEvidencePageModule)
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class LoanPageRoutingModule { }
