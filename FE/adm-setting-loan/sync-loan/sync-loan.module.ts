import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { SyncLoanPageRoutingModule } from './sync-loan-routing.module';

import { SyncLoanPage } from './sync-loan.page';
import { GlobalComponentsModule } from 'src/app/components/components.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    SyncLoanPageRoutingModule,
    GlobalComponentsModule
  ],
  declarations: [SyncLoanPage]
})
export class SyncLoanPageModule { }
