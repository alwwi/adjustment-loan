import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { TransferEvidenceDetailPage } from './transfer-evidence-detail.page';

const routes: Routes = [
  {
    path: '',
    component: TransferEvidenceDetailPage
  },  {
    path: 'add-transfer-evidence',
    loadChildren: () => import('./add-transfer-evidence/add-transfer-evidence.module').then( m => m.AddTransferEvidencePageModule)
  }

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class TransferEvidenceDetailPageRoutingModule {}
