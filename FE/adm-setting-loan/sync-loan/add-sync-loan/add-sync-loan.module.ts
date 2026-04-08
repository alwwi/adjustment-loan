import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { AddSyncLoanPageRoutingModule } from './add-sync-loan-routing.module';

import { AddSyncLoanPage } from './add-sync-loan.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    AddSyncLoanPageRoutingModule
  ],
  declarations: [AddSyncLoanPage]
})
export class AddSyncLoanPageModule {}
