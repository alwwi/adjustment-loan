import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { AddLoanCategoryPageRoutingModule } from './add-loan-category-routing.module';

import { AddLoanCategoryPage } from './add-loan-category.page';
import { GlobalComponentsModule } from 'src/app/components/components.module';
import { AddLoanCategoryDetailComponent } from './add-loan-category-detail/add-loan-category-detail.component';
import { FloatLabelModule } from 'primeng/floatlabel';
import { DropdownModule } from 'primeng/dropdown';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    AddLoanCategoryPageRoutingModule,
    GlobalComponentsModule,
    DropdownModule,
    FloatLabelModule
  ],
  declarations: [AddLoanCategoryPage, AddLoanCategoryDetailComponent]
})
export class AddLoanCategoryPageModule { }
