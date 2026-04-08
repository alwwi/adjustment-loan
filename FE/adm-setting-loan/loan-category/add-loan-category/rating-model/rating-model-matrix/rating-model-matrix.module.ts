import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { RatingModelMatrixPageRoutingModule } from './rating-model-matrix-routing.module';

import { RatingModelMatrixPage } from './rating-model-matrix.page';
import { FloatLabelModule } from 'primeng/floatlabel';
import { DropdownModule } from 'primeng/dropdown';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    RatingModelMatrixPageRoutingModule,
    DropdownModule,
    FloatLabelModule
  ],
  declarations: [RatingModelMatrixPage]
})
export class RatingModelMatrixPageModule { }
