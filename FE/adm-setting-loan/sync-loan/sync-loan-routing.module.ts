import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { SyncLoanPage } from './sync-loan.page';

const routes: Routes = [
  {
    path: '',
    component: SyncLoanPage
  },  {
    path: 'add-sync-loan',
    loadChildren: () => import('./add-sync-loan/add-sync-loan.module').then( m => m.AddSyncLoanPageModule)
  }

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class SyncLoanPageRoutingModule {}
