import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { LoanCategoryPageRoutingModule } from './loan-category-routing.module';

import { LoanCategoryPage } from './loan-category.page';
import { GlobalComponentsModule } from 'src/app/components/components.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    LoanCategoryPageRoutingModule,
    GlobalComponentsModule
  ],
  declarations: [LoanCategoryPage]
})
export class LoanCategoryPageModule { }
