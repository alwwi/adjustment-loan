import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { TransferEvidencePageRoutingModule } from './transfer-evidence-routing.module';

import { TransferEvidencePage } from './transfer-evidence.page';
import { GlobalComponentsModule } from 'src/app/components/components.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    TransferEvidencePageRoutingModule,
    GlobalComponentsModule
  ],
  declarations: [TransferEvidencePage]
})
export class TransferEvidencePageModule { }
