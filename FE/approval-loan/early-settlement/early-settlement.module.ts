import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { EarlySettlementPageRoutingModule } from './early-settlement-routing.module';

import { EarlySettlementPage } from './early-settlement.page';
import { GlobalComponentsModule } from 'src/app/components/components.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    EarlySettlementPageRoutingModule,
    GlobalComponentsModule
  ],
  declarations: [EarlySettlementPage]
})
export class EarlySettlementPageModule { }
