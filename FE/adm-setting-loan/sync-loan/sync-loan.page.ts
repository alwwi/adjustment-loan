import { Component } from '@angular/core';
import { NavController } from '@ionic/angular';
import { HttpService } from 'src/app/services/http/http.service';
import { MainService } from 'src/app/services/main/main.service';

@Component({
  selector: 'app-sync-loan',
  templateUrl: './sync-loan.page.html',
  styleUrls: ['./sync-loan.page.scss'],
})
export class SyncLoanPage {

  canRefresh: boolean = false;
  isSkeletonLoading: boolean = true;
  isLoadMoreShow: boolean = false;
  isSpinnerShow: boolean = false;
  isEmpty: boolean = false;
  indexPaging: number = 0;
  size = this.mainService.dataDisplaySize;

  status: any;
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
      type: "Sync Loan",
      status: this.status,
      startDate: this.startDate,
      endDate: this.endDate
    }

    this.mainService.filterModal(data, (res: any) => {
      if (res.data != undefined) {
        this.status = res.data.status;
        this.startDate = res.data.startDate;
        this.endDate = res.data.endDate;
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
      "/api/admin/sync/loan?page=" + this.indexPaging + "&size=" + this.size;

    if (this.status != undefined && this.status != '' && this.status != null) {
      urlApi += '&status=' + this.status;
    }

    if (this.startDate != undefined && this.startDate != '' && this.startDate != null) {
      urlApi += '&requestDateStart=' + this.mainService.convertDate(this.startDate, this.mainService.globalConstant.dateFormatToDB);
    }

    if (this.endDate != undefined && this.endDate != '' && this.endDate != null) {
      urlApi += '&requestDateEnd=' + this.mainService.convertDate(this.endDate, this.mainService.globalConstant.dateFormatToDB);
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
    this.navCtrl.navigateForward("/administration/settings/loan/sync-loan/add-sync-loan");
  }

  downloadDocs(url: any) {
    this.mainService.openInAppBrowser(url);
  }
}
