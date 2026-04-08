import { Component } from '@angular/core';
import { ActivatedRoute, NavigationExtras } from '@angular/router';
import { ModalController, NavController } from '@ionic/angular';
import { HttpService } from 'src/app/services/http/http.service';
import { MainService } from 'src/app/services/main/main.service';
import { SwalService } from 'src/app/services/swal/swal.service';
import { AddLoanTypeDetailComponent } from './add-loan-type-detail/add-loan-type-detail.component';

@Component({
  selector: 'app-loan-type',
  templateUrl: './loan-type.page.html',
  styleUrls: ['./loan-type.page.scss'],
})
export class LoanTypePage {

  id: any;
  loanCategoryId: any;

  segmentValue: string = "General";
  listSegment: any = [
    {
      title: "General",
      value: "General",
    },
    {
      title: "Attachment",
      value: "Attachment",
    },
    {
      title: "Discipline",
      value: "Discipline",
    },
    {
      title: "Criteria",
      value: "Criteria",
    }
  ];

  canRefresh: boolean = false;
  isSkeletonLoading: boolean = true;
  isLoadMoreShow: boolean = false;
  isSpinnerShow: boolean = false;
  isEmpty: boolean = false;
  indexPaging: number = 0;
  size = this.mainService.dataDisplaySize;
  listData: any = [];

  loanType: any = {
    name: null,
    description: null,
    dsrPercentage: null,
    interestRate: null,
    minTenor: null,
    maxTenor: null,
    earlySettlement: false,
    downPayment: false,
    startDate: null,
    endDate: null,
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
      this.loanCategoryId = this.mainService.decrypt(params["loanCategoryId"]);
    })
        if (this.id != null && this.id != "") {
          this.getData();
        } else {
          this.listSegment.splice(1, 3);
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
    this.loanType = {
      name: null,
      description: null,
      dsrPercentage: null,
      interestRate: null,
      minTenor: null,
      maxTenor: null,
      earlySettlement: false,
      downPayment: false,
      startDate: null,
      endDate: null,
    }
    if (this.segmentValue == "General") {
      if (this.id != null && this.id != "") {
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
    this.loanType = {
      name: null,
      description: null,
      dsrPercentage: null,
      interestRate: null,
      minTenor: null,
      maxTenor: null,
      earlySettlement: false,
      downPayment: false,
      startDate: null,
      endDate: null,
    }

    if (this.segmentValue == "General") {
      if (this.id != null && this.id != "") {
        this.getData();
      }
    } else {
      this.isEmpty = false;
      this.isSkeletonLoading = true;
      this.getListData();
    }
  }

  change(event: any, type?: any) {
    let value = event.target.value;
    value = value.replace(/\D/g, "").replace("^[1-9][0-9]*$", "");

    if (type == "Percent" && value > 100) {
      value = "100";
    }

    event.target.value = value;
  }

  toggleChange(event: any, type?: any) {
    if (type == "DP") {
      this.loanType.downPayment = event.detail.checked;
    } else {
      this.loanType.earlySettlement = event.detail.checked;
    }
  }

  getData() {
    let urlApi = this.mainService.getMainUrl() + '/api/loan/type/detail?id=' + this.id;

    this.httpService.getUrlApi(
      urlApi,
      false,
      (result: any, status: string) => {
        if (status === 'success') {
          if (result != null && result != '') {
            result.startDate = this.mainService.convertDate(result.startDate, this.mainService.globalConstant.dateFormatToDB);
            result.endDate = this.mainService.convertDate(result.endDate, this.mainService.globalConstant.dateFormatToDB);
            this.loanType = result;
          }
        } else {
          this.navCtrl.back();
          this.httpService.handlingHttpError(result);
        }
      });
  }

  getListData() {
    let urlApi = this.mainService.getMainUrl() + '/api/loan';
    if (this.segmentValue == 'Attachment') {
      urlApi += '/attachment?page=';
    } else if (this.segmentValue == 'Discipline') {
      urlApi += '/discipline?page=';
    } else if (this.segmentValue == 'Criteria') {
      urlApi += '/criteria?page=';
    }

    urlApi += this.indexPaging + '&size=' + this.size + '&loanTypeId=' + this.id;

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
    let startDate = this.mainService.convertDate(new Date(this.loanType.startDate), this.mainService.globalConstant.dateFormatToDB);
    let endDate = this.mainService.convertDate(new Date(this.loanType.endDate), this.mainService.globalConstant.dateFormatToDB);
    if (endDate < startDate) {
      this.messageValidation = "Start Date Must be Lower than End Date";
      return false;
    }
    return true;
  }

  onSubmit() {
    this.swalService.present("Are you sure to submit?", "question", (res: any) => {
      if (res) {
        if (this.verificationForm(this.loanType)) {
          let urlApi = this.mainService.getMainUrl() + '/api/loan/type';

          let dataPost: any = {};

          dataPost = this.loanType;
          dataPost.loanCategoryId = this.loanCategoryId;
          dataPost.startDate = this.mainService.convertDate(new Date(this.loanType.startDate), this.mainService.globalConstant.dateFormatToPost);
          dataPost.endDate = this.mainService.convertDate(new Date(this.loanType.endDate), this.mainService.globalConstant.dateFormatToPost);

          if (this.id != null && this.id != "") {
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
    if (this.id == null || this.id == "") {
      this.navCtrl.back();
    }
  }

  onRemove(data: any) {
    this.swalService.present(`Are you sure to delete this ${this.segmentValue}?`, "question", (res: any) => {
      if (res) {
        let urlApi = this.mainService.getMainUrl() + '/api/loan';

        if (this.segmentValue == 'Discipline') {
          urlApi += '/discipline/delete';
        } else if (this.segmentValue == 'Criteria') {
          urlApi += '/criteria/delete';
        } else if (this.segmentValue == 'Attachment') {
          urlApi += '/attachment/delete';
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
    this.openModalDetail(id);
  }

  async openModalDetail(id: any) {
    const modal = await this.modalCtrl.create({
      component: AddLoanTypeDetailComponent,
      cssClass: 'modal-search',
      mode: 'ios',
      componentProps: {
        id,
        loanTypeId: this.id,
        loanCategoryId: this.loanCategoryId,
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
    if (type == 'start' && !this.loanType.startDate) {
      this.loanType.startDate = this.mainService.convertDate(new Date(), 'yyyy-MM-dd');
    } else if (type == 'end' && !this.loanType.endDate) {
      this.loanType.endDate = this.mainService.convertDate(new Date(), 'yyyy-MM-dd');
    }
  }
}
