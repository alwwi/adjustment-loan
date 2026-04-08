import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AddTransferEvidencePage } from './add-transfer-evidence.page';

const routes: Routes = [
  {
    path: '',
    component: AddTransferEvidencePage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AddTransferEvidencePageRoutingModule {}
