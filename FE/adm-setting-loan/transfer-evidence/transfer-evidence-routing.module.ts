import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { TransferEvidencePage } from './transfer-evidence.page';

const routes: Routes = [
  {
    path: '',
    component: TransferEvidencePage
  },  {
    path: 'transfer-evidence-detail',
    loadChildren: () => import('./transfer-evidence-detail/transfer-evidence-detail.module').then( m => m.TransferEvidenceDetailPageModule)
  }

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class TransferEvidencePageRoutingModule {}
