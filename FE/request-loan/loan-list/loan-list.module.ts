import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { LoanListPageRoutingModule } from './loan-list-routing.module';

import { LoanListPage } from './loan-list.page';
import { GlobalComponentsModule } from 'src/app/components/components.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    LoanListPageRoutingModule,
    GlobalComponentsModule
  ],
  declarations: [LoanListPage]
})
export class LoanListPageModule { }
