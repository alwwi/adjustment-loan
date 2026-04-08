import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpService } from 'src/app/services/http/http.service';
import { MainService } from 'src/app/services/main/main.service';

@Component({
  selector: 'app-loan-request',
  templateUrl: './loan.page.html',
  styleUrls: ['./loan.page.scss'],
})
export class LoanPage {

  listMenu: any[] = [
    {
      title: "Loan",
      value: "Loan",
      url: "/approval/loan/loan-list",
      icon: "../../assets/icon/self-service/loan.png",
      authority: [],
      count: 0
    },
    {
      title: "Early Settlement",
      value: "Early Settlement",
      url: "/approval/loan/early-settlement",
      icon: "../../assets/icon/self-service/early-settlement.png",
      authority: [],
      count: 0
    }
  ];

  action: any;

  constructor(
    public mainService: MainService,
    private httpService: HttpService,
    private activatedRoute: ActivatedRoute
  ) { }


  ionViewWillEnter() {
    this.listMenu.forEach(el => {
      el.count = 0;
    });
        this.mainService.checkMenu(this.listMenu).then(res => {
          this.listMenu = res;
        });
        this.activatedRoute.queryParams.subscribe((params) => {
          this.action = params["action"];
        });
        if (this.action == "Need Approval") {
          this.getCountNeedApproval();
        }
  }

  getCountNeedApproval() {
    let urlApi = this.mainService.getMainUrl() +
      "/api/user/dataapproval/countneedapproval/allmodule";

    this.httpService.getUrlApi(urlApi, false, (res: any, status: any) => {
      if (status == "success") {
        let loanCount = res.filter((result: any) => result.name == "Loan");
        let earlyCount = res.filter((result: any) => result.name == "Early Settlement");

        this.listMenu.forEach((el: any) => {
          for (let i = 0, len = res.length; i < len; i++) {
            if (el.value == "Loan") {
              el.count = parseInt(loanCount.length > 0 ? loanCount[0].value : 0);
            } else if (el.value == "Early Settlement") {
              el.count = parseInt(earlyCount.length > 0 ? earlyCount[0].value : 0);
            }
          }
        });
      } else {
        this.httpService.handlingHttpError(res);
      }
    });
  }
}
