import { Component } from '@angular/core';
import { NavigationExtras } from '@angular/router';
import { NavController } from '@ionic/angular';
import { HttpService } from 'src/app/services/http/http.service';
import { MainService } from 'src/app/services/main/main.service';
import { SwalService } from 'src/app/services/swal/swal.service';

@Component({
  selector: 'app-loan-category',
  templateUrl: './loan-category.page.html',
  styleUrls: ['./loan-category.page.scss'],
})
export class LoanCategoryPage {

  canRefresh: boolean = false;
  isSkeletonLoading: boolean = true;
  isLoadMoreShow: boolean = false;
  isSpinnerShow: boolean = false;
  isEmpty: boolean = false;
  indexPaging: number = 0;
  size = this.mainService.dataDisplaySize;

  filterName: any;
  startDate: any;
  endDate: any;
  listData: any = [];

  constructor(
    public mainService: MainService,
    private swalService: SwalService,
    private httpService: HttpService,
    private navCtrl: NavController
  ) { }

  ionViewWillEnter() {
        this.getListData();
  }

  doRefresh(event: any) {
    this.indexPaging = 0;
    this.isSkeletonLoading = true;
    this.getListData();
    setTimeout(() => {
      event.target.complete();
    }, 500);
  }

  filter() {
    let data = {
      type: "Vehicle Type",
      filterName: this.filterName,
      startDate: this.startDate,
      endDate: this.endDate,
    }

    this.mainService.filterModal(data, (res: any) => {
      if (res.data != undefined) {
        this.filterName = res.data.filterName;
        this.startDate = res.data.startDate;
        this.endDate = res.data.endDate;
        this.isSkeletonLoading = true;
        this.listData = [];
        this.indexPaging = 0;
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
      "/api/loan/category?page=" + this.indexPaging + "&size=" + this.size;

    if (this.filterName != undefined && this.filterName != '' && this.filterName != null) {
      urlApi = urlApi + '&name=' + this.filterName;
    }

    if (this.startDate != undefined && this.startDate != '' && this.startDate != null) {
      urlApi = urlApi + '&startDate=' + this.mainService.convertDate(this.startDate, this.mainService.globalConstant.dateFormatToDB);
    }

    if (this.endDate != undefined && this.endDate != '' && this.endDate != null) {
      urlApi = urlApi + '&endDate=' + this.mainService.convertDate(this.endDate, this.mainService.globalConstant.dateFormatToDB);
    }

    this.httpService.getUrlApi(urlApi, false, (res: any, status: any) => {
      if (status == "success") {
        if (this.indexPaging == 0) {
          this.listData = res.content;
        } else {
          res.content.forEach((el: any) => this.listData.push(el));
        }

        this.isEmpty = res.empty;
        this.isLoadMoreShow = !res.last;
      } else {
        this.isEmpty = true;
        this.httpService.handlingHttpError(res);
      }
      this.isSkeletonLoading = false;
      this.isSpinnerShow = false;
    });
  }

  onRemove(data: any) {
    this.swalService.present("Are you sure to delete this Loan Category ?", "question", (res: any) => {
      if (res) {
        let urlApi = this.mainService.getMainUrl() +
          '/api/loan/category/delete';

        let dataPost: any = {
          id: data.id
        };

        this.httpService.postUrlApi(
          urlApi,
          JSON.stringify(dataPost),
          true,
          null,
          (result: any, status: string) => {
            if (status === 'success') {
              this.successDelete(result);
            } else {
              this.httpService.handlingHttpError(result);
            }
          });
      }
    });
  }

  successDelete(res: any) {
    this.mainService.dismissLoading();
    this.swalService.present(res.message, "success", () => {
      this.isSkeletonLoading = true;
      this.listData = [];
      this.getListData();
    });
  }

  goToDetail(id: any) {
    let params: NavigationExtras = {
      queryParams: {
        id: this.mainService.encrypt(id),
      },
    };
    if (id == null) {
      this.navCtrl.navigateForward("/administration/settings/loan/loan-category/add-loan-category");
    } else {
      this.navCtrl.navigateForward("/administration/settings/loan/loan-category/add-loan-category", params);
    }
  }
}
