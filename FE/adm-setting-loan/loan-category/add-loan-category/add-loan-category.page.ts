import { Component } from '@angular/core';
import { AddLoanCategoryDetailComponent } from './add-loan-category-detail/add-loan-category-detail.component';
import { ActivatedRoute, NavigationExtras } from '@angular/router';
import { MainService } from 'src/app/services/main/main.service';
import { HttpService } from 'src/app/services/http/http.service';
import { SwalService } from 'src/app/services/swal/swal.service';
import { ModalController, NavController } from '@ionic/angular';

@Component({
  selector: 'app-add-loan-category',
  templateUrl: './add-loan-category.page.html',
  styleUrls: ['./add-loan-category.page.scss'],
})
export class AddLoanCategoryPage {

  id: any;

  segmentValue: string = "General";
  listSegment: any = [
    {
      title: "General",
      value: "General",
    },
    {
      title: "Loan Type",
      value: "Loan Type",
    },
    {
      title: "Vehicle Brand",
      value: "Vehicle Brand",
    },
    {
      title: "Plafond Bracket",
      value: "Plafond Bracket",
    },
    {
      title: "Allowance Bracket",
      value: "Allowance Bracket",
    },
    {
      title: "Feedback Fleet",
      value: "Feedback Fleet",
    },
    {
      title: "Rating Model",
      value: "Rating Model",
    },
  ];

  canRefresh: boolean = false;
  isSkeletonLoading: boolean = true;
  isLoadMoreShow: boolean = false;
  isSpinnerShow: boolean = false;
  isEmpty: boolean = false;
  indexPaging: number = 0;
  size = this.mainService.dataDisplaySize;
  listData: any = [];

  loanCategory: any = {
    name: null,
    description: null,
    simulation: false
  };
  messageValidation: string = "";

  constructor(
    private activatedRoute: ActivatedRoute,
    public mainService: MainService,
    private httpService: HttpService,
    private swalService: SwalService,
    private navCtrl: NavController,
    private modalCtrl: ModalController
  ) { }

  ionViewWillEnter() {
    this.activatedRoute.queryParams.subscribe((params) => {
      this.id = this.mainService.decrypt(params["id"]);
    })
        if (this.id != null) {
          this.getData();
        } else {
          this.listSegment.splice(1, 6);
        }
  }

  loadMore() {
    this.isSpinnerShow = true;
    this.indexPaging++;
    this.getListData();
  }

  doRefresh(event: any) {
    this.isSkeletonLoading = false;
    this.isEmpty = false;
    this.isLoadMoreShow = false;
    this.isSpinnerShow = false;
    this.listData = [];
    this.loanCategory = {
      name: null,
      description: null,
      simulation: false,
    }
    if (this.segmentValue == "General") {
      if (this.id != null) {
        this.getData();
      }
    } else {
      this.isEmpty = false;
      this.getListData();
    }
    setTimeout(() => {
      event.target.complete();
    }, 500);
  }

  selectValue(event: any) {
    this.segmentValue = event.detail.value;
    this.isSkeletonLoading = false;
    this.isEmpty = false;
    this.isLoadMoreShow = false;
    this.isSpinnerShow = false;
    this.listData = [];
    this.loanCategory = {
      name: null,
      description: null,
      isActive: false,
    }

    if (this.segmentValue == "General") {
      this.getData();
    } else {
      this.isEmpty = false;
      this.isSkeletonLoading = true;
      this.getListData();
    }
  }

  openSearchMenu(type: string) {
    let data: any = {
      searchType: type,
    }

    this.mainService.searchModal(data, (res: any) => {
      if (res.data != undefined) {
        this.loanCategory.reqCatTypeName = res.data.name;
        this.loanCategory.categoryId = res.data.id;
      }
    });
  }

  change(event: any) {
    let value = event.target.value;
    value = value.replace(/\D/g, "").replace("^[1-9][0-9]*$", "");
    event.target.value = value;
  }

  toggleChange(event: any) {
    this.loanCategory.simulation = event.detail.checked;
  }

  getData() {
    let urlApi = this.mainService.getMainUrl() + '/api/loan/category/detail?id=' + this.id;

    this.httpService.getUrlApi(
      urlApi,
      false,
      (result: any, status: string) => {
        if (status === 'success') {
          if (result != null && result != '') {
            result.startDate = this.mainService.convertDate(result.startDate, this.mainService.globalConstant.dateFormatToDB);
            result.endDate = this.mainService.convertDate(result.endDate, this.mainService.globalConstant.dateFormatToDB);
            this.loanCategory = result;
            this.loanCategory.reqCatTypeName = result.requestCategoryTypeName;
            this.loanCategory.categoryId = result.requestCategoryTypeId;
          }
        } else {
          this.navCtrl.back();
          this.httpService.handlingHttpError(result);
        }
      });
  }

  getListData() {
    let urlApi = this.mainService.getMainUrl() + '/api/loan';
    if (this.segmentValue == 'Loan Type') {
      urlApi += '/type?page=';
    } else if (this.segmentValue == 'Vehicle Brand') {
      urlApi += '/vehicle-brand?page=';
    } else if (this.segmentValue == 'Plafond Bracket') {
      urlApi += '/plafond-bracket?page=';
    } else if (this.segmentValue == 'Allowance Bracket') {
      urlApi += '/allowance-bracket?page=';
    } else if (this.segmentValue == 'Feedback Fleet') {
      urlApi += '/feedback-fleet?page=';
    } else if (this.segmentValue == 'Rating Model') {
      urlApi += '/rating-model?page=';
    }

    urlApi += this.indexPaging + '&size=' + this.size + '&loanCategoryId=' + this.id;

    this.httpService.getUrlApi(
      urlApi,
      false,
      (result: any, status: string) => {
        if (status === 'success') {
          if (this.indexPaging == 0) {
            this.listData = result.content;
          } else {
            result.content.forEach((el: any) => this.listData.push(el));
          }

          if ((this.segmentValue == 'Plafond Bracket' || this.segmentValue == 'Allowance Bracket') && this.listData.length > 0) {
            this.listData.forEach((element: any) => {
              element.minValueString = element.minValue != null ? "Rp " + `${element.minValue}`.replace(/\D/g, "").replace(/\B(?=(\d{3})+(?!\d))/g, ".") : '';
              element.maxValueString = element.maxValue != null ? "- Rp " + `${element.maxValue}`.replace(/\D/g, "").replace(/\B(?=(\d{3})+(?!\d))/g, ".") : '';
              element.valueString = element.minValueString + " " + element.maxValueString;
            });
          }

          this.isEmpty = result.empty;
          this.isLoadMoreShow = !result.last;
        } else {
          this.navCtrl.back();
          this.httpService.handlingHttpError(result);
        }
        this.isSpinnerShow = false;
        this.isSkeletonLoading = false;
      });
  }

  verificationForm(form: any) {
    let startDate = this.mainService.convertDate(new Date(this.loanCategory.startDate), this.mainService.globalConstant.dateFormatToDB);
    let endDate = this.mainService.convertDate(new Date(this.loanCategory.endDate), this.mainService.globalConstant.dateFormatToDB);
    if (endDate < startDate) {
      this.messageValidation = "Start Date Must be Lower than End Date";
      return false;
    }
    return true;
  }

  onSubmit() {
    this.swalService.present("Are you sure to submit?", "question", (res: any) => {
      if (res) {
        if (this.verificationForm(this.loanCategory)) {
          let urlApi = this.mainService.getMainUrl() + '/api/loan/category';

          let dataPost: any = {};

          dataPost = this.loanCategory;
          dataPost.simulation = this.loanCategory.simulation ? true : false;
          dataPost.requestCategoryTypeId = this.loanCategory.categoryId;
          dataPost.startDate = this.mainService.convertDate(new Date(this.loanCategory.startDate), this.mainService.globalConstant.dateFormatToPost);
          dataPost.endDate = this.mainService.convertDate(new Date(this.loanCategory.endDate), this.mainService.globalConstant.dateFormatToPost);

          if (this.id != null) {
            dataPost.id = this.id;
          }

          this.httpService.postUrlApi(
            urlApi,
            JSON.stringify(dataPost),
            true,
            null,
            (result: any, status: string) => {
              if (status === 'success') {
                this.handleSuccessSubmit(result);
              } else {
                this.httpService.handlingHttpError(result);
              }
            });
        } else {
          this.swalService.present(this.messageValidation, "error");
        }
      }
    });
  }

  handleSuccessSubmit(data: any) {
    this.mainService.dismissLoading();
    this.swalService.present(data.message, "success");
    if (this.id == null) {
      this.navCtrl.back();
    }
  }

  onRemove(data: any) {
    this.swalService.present(`Are you sure to delete this ${this.segmentValue}?`, "question", (res: any) => {
      if (res) {
        let urlApi = this.mainService.getMainUrl() + '/api/loan';

        if (this.segmentValue == 'Loan Type') {
          urlApi += '/type/delete';
        } else if (this.segmentValue == 'Vehicle Brand') {
          urlApi += '/vehicle-brand/delete';
        } else if (this.segmentValue == 'Plafond Bracket') {
          urlApi += '/plafond-bracket/delete';
        } else if (this.segmentValue == 'Allowance Bracket') {
          urlApi += '/allowance-bracket/delete';
        } else if (this.segmentValue == 'Feedback Fleet') {
          urlApi += '/feedback-fleet/delete';
        } else if (this.segmentValue == 'Rating Model') {
          urlApi += '/rating-model/delete';
        }

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

  addDetail(id: any) {
    let params: NavigationExtras = {
      queryParams: {
        id: this.mainService.encrypt(id),
        loanCategoryId: this.mainService.encrypt(this.id)
      },
    };

    if (this.segmentValue === 'Loan Type') {
      if (id == null) {
        this.navCtrl.navigateForward("/administration/settings/loan/loan-category/add-loan-category/loan-type", params);
      } else {
        this.navCtrl.navigateForward("/administration/settings/loan/loan-category/add-loan-category/loan-type", params);
      }
    } else if (this.segmentValue === 'Rating Model') {
      if (id == null) {
        this.navCtrl.navigateForward("/administration/settings/loan/loan-category/add-loan-category/rating-model", params);
      } else {
        this.navCtrl.navigateForward("/administration/settings/loan/loan-category/add-loan-category/rating-model", params);
      }
    } else {
      this.openModalDetail(id);
    }
  }

  async openModalDetail(id: any) {
    const modal = await this.modalCtrl.create({
      component: AddLoanCategoryDetailComponent,
      cssClass: 'modal-search',
      mode: 'ios',
      componentProps: {
        id,
        categoryId: this.id,
        type: this.segmentValue
      },
    });

    modal.onDidDismiss().then((data) => {
      if (data.data != undefined) {
        this.swalService.present(data.data, "success", () => {
          this.isSkeletonLoading = true;
          this.listData = [];
          this.getListData();
        });
      }
    });

    return await modal.present();
  }

  onChangeDate(type: string) {
    if (type == 'start' && !this.loanCategory.startDate) {
      this.loanCategory.startDate = this.mainService.convertDate(new Date(), 'yyyy-MM-dd');
    } else if (type == 'end' && !this.loanCategory.endDate) {
      this.loanCategory.endDate = this.mainService.convertDate(new Date(), 'yyyy-MM-dd');
    }
  }
}
