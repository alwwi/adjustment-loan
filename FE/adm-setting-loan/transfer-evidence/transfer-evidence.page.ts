import { Component } from '@angular/core';
import { NavigationExtras } from '@angular/router';
import { NavController } from '@ionic/angular';
import { HttpService } from 'src/app/services/http/http.service';
import { MainService } from 'src/app/services/main/main.service';

@Component({
  selector: 'app-transfer-evidence',
  templateUrl: './transfer-evidence.page.html',
  styleUrls: ['./transfer-evidence.page.scss'],
})
export class TransferEvidencePage {

  canRefresh: boolean = false;
  isSkeletonLoading: boolean = true;
  isLoadMoreShow: boolean = false;
  isSpinnerShow: boolean = false;
  isEmpty: boolean = false;
  indexPaging: number = 0;
  size = this.mainService.dataDisplaySize;

  requestNo: any;
  status: any;
  loanStatus: any = "Approved";
  loanType: any;
  startDate: any;
  endDate: any;
  listData: any = [];

  constructor(
    public mainService: MainService,
    private httpService: HttpService,
    private navCtrl: NavController
  ) { }

  ionViewWillEnter() {
        this.getListData();
  }

  doRefresh(event: any) {
    this.isSkeletonLoading = true;
    this.indexPaging = 0;
    this.getListData();
    setTimeout(() => {
      event.target.complete();
    }, 500);
  }

  filter() {
    let data = {
      type: "Transfer Evidence",
      status: this.status,
      startDate: this.startDate,
      endDate: this.endDate,
      loanType: this.loanType,
      loanStatus: this.loanStatus,
      requestNo: this.requestNo
    }

    this.mainService.filterModal(data, (res: any) => {
      if (res.data != undefined) {
        this.status = res.data.status;
        this.startDate = res.data.startDate;
        this.endDate = res.data.endDate;
        this.loanType = res.data.loanType;
        this.loanStatus = res.data.loanStatus;
        this.requestNo = res.data.requestNo;
        this.isSkeletonLoading = true;
        this.indexPaging = 0;
        this.listData = [];
        this.getListData();
      }
    })
  }

  loadMore() {
    this.isSpinnerShow = true;
    this.indexPaging++;
    this.getListData();
  }

  getListData() {
    let urlApi = this.mainService.getMainUrl() +
      "/api/loan/transfer-evidence/loan?page=" + this.indexPaging + "&size=" + this.size;

    if (this.requestNo != undefined && this.requestNo != '' && this.requestNo != null) {
      urlApi += '&requestNo=' + this.requestNo;
    }

    if (this.status != undefined && this.status != '' && this.status != null) {
      urlApi += '&anyTransfer=' + this.status;
    }

    if (this.loanType != undefined && this.loanType != '' && this.loanType != null) {
      urlApi += '&loanTypeId=' + this.loanType;
    }

    if (this.loanStatus != undefined && this.loanStatus != '' && this.loanStatus != null) {
      urlApi += '&status=' + this.loanStatus;
    }

    if (this.startDate != undefined && this.startDate != '' && this.startDate != null) {
      urlApi += '&startDate=' + this.mainService.convertDate(this.startDate, this.mainService.globalConstant.dateFormatToDB);
    }

    if (this.endDate != undefined && this.endDate != '' && this.endDate != null) {
      urlApi += '&endDate=' + this.mainService.convertDate(this.endDate, this.mainService.globalConstant.dateFormatToDB);
    }


    this.httpService.getUrlApi(urlApi, false, (res: any, status: any) => {
      if (status == "success") {
        if (this.indexPaging == 0) {
          this.listData = res.content;
        } else {
          res.content.forEach((el: any) => this.listData.push(el));
        }

        this.isEmpty = this.listData.length == 0;
        this.isLoadMoreShow = !res.last;
      } else {
        this.isEmpty = true;
        this.httpService.handlingHttpError(res);
      }
      this.isSkeletonLoading = false;
      this.isSpinnerShow = false;
    });
  }

  goToDetail(id: any) {
    let params: NavigationExtras = {
      queryParams: {
        id: this.mainService.encrypt(id),
      },
    };

    this.navCtrl.navigateForward("/administration/settings/loan/transfer-evidence/transfer-evidence-detail", params);
  }
}
