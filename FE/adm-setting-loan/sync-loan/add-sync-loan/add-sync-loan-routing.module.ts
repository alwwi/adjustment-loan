import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AddSyncLoanPage } from './add-sync-loan.page';

const routes: Routes = [
  {
    path: '',
    component: AddSyncLoanPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AddSyncLoanPageRoutingModule {}
