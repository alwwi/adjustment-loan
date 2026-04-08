import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { RatingModelPageRoutingModule } from './rating-model-routing.module';

import { RatingModelPage } from './rating-model.page';
import { GlobalComponentsModule } from 'src/app/components/components.module';
import { FloatLabelModule } from 'primeng/floatlabel';
import { DropdownModule } from 'primeng/dropdown';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    RatingModelPageRoutingModule,
    GlobalComponentsModule,
    DropdownModule,
    FloatLabelModule
  ],
  declarations: [RatingModelPage]
})
export class RatingModelPageModule { }
