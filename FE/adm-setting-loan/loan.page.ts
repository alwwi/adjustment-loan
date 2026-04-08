import { Component } from '@angular/core';
import { MainService } from 'src/app/services/main/main.service';

@Component({
  selector: 'app-loan-settings',
  templateUrl: './loan.page.html',
  styleUrls: ['./loan.page.scss'],
})
export class LoanPage {

  listMenu: any[] = [
    {
      title: "Loan Category",
      value: "Loan Category",
      url: "/administration/settings/loan/loan-category",
      icon: "../../../../assets/icon/administration/loan/loan-category.png",
      authority: ['Loan Admin'],
    },
    {
      title: "Vehicle Type",
      value: "Vehicle Type",
      url: "/administration/settings/loan/vehicle-type",
      icon: "../../../../assets/icon/administration/loan/vehicle-type.png",
      authority: ['Loan Admin'],
    },
    {
      title: "Sync Loan",
      value: "Sync Loan",
      url: "/administration/settings/loan/sync-loan",
      icon: "../../../../assets/icon/administration/loan/sync-loan.png",
      authority: ['Loan Admin'],
    },
    {
      title: "Transfer Evidence",
      value: "Transfer Evidence",
      url: "/administration/settings/loan/transfer-evidence",
      icon: "../../../../assets/icon/administration/loan/transfer-envidence.png",
      authority: ['Loan Admin'],
    },
  ];

  constructor(
    public mainService: MainService
  ) { }

  ionViewWillEnter() {
    this.mainService.checkMenu(this.listMenu).then(res => {
      this.listMenu = res;
    })
  }

}
