import { Component } from '@angular/core';
import { ActivatedRoute, NavigationExtras } from '@angular/router';
import { NavController } from '@ionic/angular';
import { HttpService } from 'src/app/services/http/http.service';
import { MainService } from 'src/app/services/main/main.service';
import { SwalService } from 'src/app/services/swal/swal.service';

@Component({
  selector: 'app-rating-model-loan',
  templateUrl: './rating-model.page.html',
  styleUrls: ['./rating-model.page.scss'],
})
export class RatingModelPage {

  id: any;
  loanCategoryId: any;
  loanRatingModelId: any;

  segmentValue: string = "General";
  listSegment: any = [
    {
      title: "General",
      value: "General",
    },
    {
      title: "Matrix",
      value: "Matrix",
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
  listLookupYear: any = [];
  listRating: any = [];

  ratingModel: any = {
    name: null,
    description: null,
  };
  messageValidation: string = "";

  constructor(
    private activatedRoute: ActivatedRoute,
    public mainService: MainService,
    private httpService: HttpService,
    private swalService: SwalService,
    private navCtrl: NavController
  ) { }

  ionViewWillEnter() {
    this.activatedRoute.queryParams.subscribe((params) => {
      this.id = this.mainService.decrypt(params["id"]);
      this.loanCategoryId = this.mainService.decrypt(params["loanCategoryId"]);
    })
        if (this.id != null && this.id != "") {
          this.getData();
        } else {
          this.listSegment.splice(1, 1);
        }
        this.getGrade();
        this.getLookUpYear();
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
    this.ratingModel = {
      name: null,
      description: null,
    }
    if (this.segmentValue == "General") {
      if (this.id != null && this.id != "") {
        this.getData();
      }
      this.getGrade();
      this.getLookUpYear();
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
    this.ratingModel = {
      name: null,
      description: null,
      isActive: false,
    }

    if (this.segmentValue == "General") {
      if (this.id != null && this.id != "") {
        this.getData();
      }
      this.getGrade();
      this.getLookUpYear();
    } else {
      this.isEmpty = false;
      this.isSkeletonLoading = true;
      this.getListData();
    }
  }

  changeLookupYear() {
    if (this.ratingModel.lookupYear == 2) {
      this.ratingModel.ratingId = "";
    }
  }

  getData() {
    let urlApi = this.mainService.getMainUrl() + '/api/loan/rating-model/detail?id=' + this.id;

    this.httpService.getUrlApi(
      urlApi,
      false,
      (result: any, status: string) => {
        if (status === 'success') {
          if (result != null && result != '') {
            result.startDate = this.mainService.convertDate(result.startDate, this.mainService.globalConstant.dateFormatToDB);
            result.endDate = this.mainService.convertDate(result.endDate, this.mainService.globalConstant.dateFormatToDB);
            this.ratingModel = result;
            this.ratingModel.lookupYear = result.yearBefore;
            this.loanRatingModelId = result.id;
          }
        } else {
          this.navCtrl.back();
          this.httpService.handlingHttpError(result);
        }
      });
  }

  getLookUpYear() {
    let urlApi = this.mainService.getMainUrl() + '/api/loan/rating-model/lookup-year-before';

    this.httpService.getUrlApi(
      urlApi,
      false,
      (result: any, status: string) => {
        if (status === 'success') {
          if (result != null && result != '') {
            this.listLookupYear = result;
          }
        } else {
          this.navCtrl.back();
          this.httpService.handlingHttpError(result);
        }
      });
  }

  getGrade() {
    let urlApi = this.mainService.getMainUrl() + '/api/admin/rating';

    this.httpService.getUrlApi(
      urlApi,
      false,
      (result: any, status: string) => {
        if (status === 'success') {
          if (result != null && result != '') {
            this.listRating = result.content;
          }
        } else {
          this.navCtrl.back();
          this.httpService.handlingHttpError(result);
        }
      });
  }

  getListData() {
    let urlApi = this.mainService.getMainUrl() + '/api/loan/rating-model-matrix?page=' + this.indexPaging + '&size=' + this.size
      + '&ratingModelId=' + this.id;

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
    let startDate = this.mainService.convertDate(new Date(this.ratingModel.startDate), this.mainService.globalConstant.dateFormatToDB);
    let endDate = this.mainService.convertDate(new Date(this.ratingModel.endDate), this.mainService.globalConstant.dateFormatToDB);
    if (endDate < startDate) {
      this.messageValidation = "Start Date Must be Lower than End Date";
      return false;
    }
    return true;
  }

  onSubmit() {
    this.swalService.present("Are you sure to submit?", "question", (res: any) => {
      if (res) {
        if (this.verificationForm(this.ratingModel)) {
          let urlApi = this.mainService.getMainUrl() + '/api/loan/rating-model';

          let dataPost: any = {};

          dataPost = this.ratingModel;
          this.ratingModel.rating = this.ratingModel.ratingId;
          dataPost.loanCategoryId = this.loanCategoryId;
          dataPost.yearBefore = this.ratingModel.lookupYear;
          dataPost.requestCategoryTypeId = this.ratingModel.categoryId;
          dataPost.startDate = this.mainService.convertDate(new Date(this.ratingModel.startDate), this.mainService.globalConstant.dateFormatToPost);
          dataPost.endDate = this.mainService.convertDate(new Date(this.ratingModel.endDate), this.mainService.globalConstant.dateFormatToPost);

          if (this.id != null && this.id != "") {
            dataPost.id = this.id;
          } else {
            dataPost.id = null;
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
    this.loanRatingModelId = data.id;
    this.swalService.present(data.message, "success");
    if (this.id != null && this.id != "") {
      this.navCtrl.back();
    }
  }

  onRemove(data: any) {
    this.swalService.present(`Are you sure to delete this ${this.segmentValue}?`, "question", (res: any) => {
      if (res) {
        let urlApi = this.mainService.getMainUrl() + '/api/loan/rating-model-matrix/delete';

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
        ratingModelId: this.loanRatingModelId
      },
    };

    this.navCtrl.navigateForward("/administration/settings/loan/loan-category/add-loan-category/rating-model/rating-model-matrix", params);
  }

  onChangeDate(type: string) {
    if (type == 'start' && !this.ratingModel.startDate) {
      this.ratingModel.startDate = this.mainService.convertDate(new Date(), 'yyyy-MM-dd');
    } else if (type == 'end' && !this.ratingModel.endDate) {
      this.ratingModel.endDate = this.mainService.convertDate(new Date(), 'yyyy-MM-dd');
    }
  }
}
