import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { AddTransferEvidencePageRoutingModule } from './add-transfer-evidence-routing.module';

import { AddTransferEvidencePage } from './add-transfer-evidence.page';
import { FileUploadModule } from 'primeng/fileupload';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    AddTransferEvidencePageRoutingModule,
    FileUploadModule
  ],
  declarations: [AddTransferEvidencePage]
})
export class AddTransferEvidencePageModule { }
