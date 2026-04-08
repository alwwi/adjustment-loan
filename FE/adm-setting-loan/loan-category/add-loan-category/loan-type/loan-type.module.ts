import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { LoanTypePageRoutingModule } from './loan-type-routing.module';

import { LoanTypePage } from './loan-type.page';
import { FloatLabelModule } from 'primeng/floatlabel';
import { DropdownModule } from 'primeng/dropdown';
import { GlobalComponentsModule } from 'src/app/components/components.module';
import { AddLoanTypeDetailComponent } from './add-loan-type-detail/add-loan-type-detail.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    LoanTypePageRoutingModule,
    GlobalComponentsModule,
    DropdownModule,
    FloatLabelModule
  ],
  declarations: [LoanTypePage, AddLoanTypeDetailComponent]
})
export class LoanTypePageModule { }
