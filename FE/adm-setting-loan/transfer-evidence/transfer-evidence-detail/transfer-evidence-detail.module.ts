import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { TransferEvidenceDetailPageRoutingModule } from './transfer-evidence-detail-routing.module';

import { TransferEvidenceDetailPage } from './transfer-evidence-detail.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    TransferEvidenceDetailPageRoutingModule
  ],
  declarations: [TransferEvidenceDetailPage]
})
export class TransferEvidenceDetailPageModule {}
