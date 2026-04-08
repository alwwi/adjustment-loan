import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { LoanTypePage } from './loan-type.page';

const routes: Routes = [
  {
    path: '',
    component: LoanTypePage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class LoanTypePageRoutingModule {}
