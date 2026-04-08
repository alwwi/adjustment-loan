import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NavController } from '@ionic/angular';
import { HttpService } from 'src/app/services/http/http.service';
import { MainService } from 'src/app/services/main/main.service';
import { SwalService } from 'src/app/services/swal/swal.service';

@Component({
  selector: 'app-loan-request-detail',
  templateUrl: './loan.page.html',
  styleUrls: ['./loan.page.scss'],
})
export class LoanPage {

  action: any;
  id: any;
  dataApprovalId: any;
  isSkeletonLoading: boolean = true;
  isSkeletonLoadingHeader: boolean = true;
  isEmpty: boolean = false;
  employee: any;
  dataDetail: any;
  listData: any = [];
  segmentValue: string = "General";
  listSegment: any = [
    {
      title: "General",
      value: "General",
    },
    {
      title: "Approval History",
      value: "Approval History",
    },
  ];

  format: any = this.mainService.globalConstant;
  confirmPayload: any = [];

  constructor(
    private activatedRoute: ActivatedRoute,
    public mainService: MainService,
    private httpService: HttpService,
    private navCtrl: NavController,
    private swalService: SwalService,
  ) { }

  ionViewWillEnter() {
    this.activatedRoute.queryParams.subscribe((params) => {
      this.id = this.mainService.decrypt(params["id"]);
      this.action = params["action"];
    });
    this.getData();
  }

  changeAmount(event: any, idx: any) {
    let value = event.target.value;
    value = value.replace(/\D/g, "").replace(/\B(?=(\d{3})+(?!\d))/g, ".");
    event.target.value = value;
    this.confirmPayload[idx] = value;
  };

  changeAmountVal(event: any) {
    let value = event.toString();
    value = value.replace(/\D/g, "").replace(/\B(?=(\d{3})+(?!\d))/g, ".");
    return value;
  };

  getData() {
    let urlApi = this.mainService.getMainUrl() + '/api/user/dataApproval/personal/loan';

    if (this.action == "Need Approval") {
      urlApi += '/needapproval/' + this.id;
    } else {
      urlApi += '/myrequest/' + this.id;
    }

    this.httpService.getUrlApi(
      urlApi,
      false,
      (result: any, status: string) => {
        if (status === 'success') {
          if (result != null && result != '') {
            this.dataDetail = result;
            this.employee = result.vwEmpAssignment;
            this.dataApprovalId = result.dataApprovalId;
            if (this.action == "Need Approval") {
              setTimeout(() => {
                for (let idx = 0; idx < this.dataDetail?.loanFeedbackFleetListDTOS.length; idx++) {
                  const el = this.dataDetail?.loanFeedbackFleetListDTOS[idx];
                  if (el.value != null) {
                    this.confirmPayload[idx] = this.changeAmountVal(el.value);
                  } else {
                    this.confirmPayload[idx] = null;
                  }
                }
              }, 500);
            }
          }
        } else {
          this.navCtrl.back();
          this.httpService.handlingHttpError(result);
        }
        this.isSkeletonLoading = false;
        this.isSkeletonLoadingHeader = false;
      });
  }

  getListData() {
    let urlApi = this.mainService.getMainUrl() + '/api/user/dataApproval/personal/loan/historyApprovalById/';

    if (this.action == "Approval History") {
      urlApi += this.dataApprovalId;
    } else {
      urlApi += this.id;
    }

    this.httpService.getUrlApi(
      urlApi,
      false,
      (result: any, status: string) => {
        if (status === 'success') {
          this.listData = result;
          this.isEmpty = this.listData.length == 0;
        } else {
          this.isEmpty = true;
          this.httpService.handlingHttpError(result);
        }
        this.isSkeletonLoading = false;
      });
  }

  selectValue(event: any) {
    this.segmentValue = event.detail.value;
    this.isSkeletonLoading = true;
    this.isEmpty = false;
    this.listData = [];

    if (this.segmentValue == "General") {
      this.getData();
    } else {
      this.getListData();
    }
  }

  showNote(actionNote?: string) {
    this.mainService.showNoteModal(actionNote);
  }

  downloadDocs(url: any) {
    this.mainService.openInAppBrowser(encodeURI(url));
  }

  cancelRequest() {
    this.swalService.present("Are you sure want to Cancel this request", "question", ((res: any) => {
      if (res) {
        let urlApi = this.mainService.getMainUrl() + '/api/user/dataApproval/actionApproval';

        let dataPost: any = {
          dataApprovalId: this.id,
          status: "Canceled",
          reasonReject: ""
        };

        this.httpService.postUrlApi(
          urlApi,
          JSON.stringify(dataPost),
          true,
          null,
          (result: any, status: string) => {
            if (status === 'success') {
              this.mainService.dismissLoading()
              this.swalService.present(result.message, "success", ((res: any) => {
                this.navCtrl.back();
              }));
            } else {
              this.httpService.handlingHttpError(result);
            }
          });
      }
    }));
  }

  confirmAction(type: any) {
    this.mainService.showApprovalNoteModal(type, (res: any) => {
      if (res.data != undefined) {
        if (res.data == "" && type == 'Reject') {
          this.swalService.present("Notes can not empty!", "error");
        } else {
          this.sendApproval(type, res.data);
        }
      }
    });
  }

  async sendApproval(type: any, note: any) {
    let urlApi = this.mainService.getMainUrl() + '/api/user/dataApproval/actionApproval';

    let dataPost: any = {
      dataApprovalId: this.id,
      actionNote: note,
    };

    if (type == 'Approve' && this.action == "Need Approval") {
      dataPost.additionalInformation = [];
      if (this.action == 'Need Approval' && this.dataDetail?.needAdditionalInformation && (this.dataDetail?.approvalLevelFleet == this.dataDetail?.currentApprovalLevel)) {
        let additional: any = {};
        for (let i = 0; i < this.confirmPayload.length; i++) {
          const element = this.confirmPayload[i];
          for (let idx = 0; idx < this.dataDetail?.loanFeedbackFleetListDTOS.length; idx++) {
            const el = this.dataDetail?.loanFeedbackFleetListDTOS[idx];
            if (idx == i) {
              if (el.valueSource == 'Monthly Installment' || el.valueSource == 'Monthly Installment MOP') {
                additional.monthlyInstallment = element != null && element != "" && element != undefined ? element.replaceAll(".", "") : null;
              } else {
                additional[el.id] = element != null && element != "" && element != undefined ? element.replaceAll(".", "") : null;
              }
            }
          }
        }

        await dataPost.additionalInformation.push(additional);
      }
    }

    if (type == 'Approve') {
      dataPost.status = "Approved";
    } else if (type == 'Reject') {
      dataPost.status = "Rejected";
    }

    this.httpService.postUrlApi(
      urlApi,
      JSON.stringify(dataPost),
      true,
      null,
      (result: any, status: string) => {
        if (status === 'success') {
          this.mainService.dismissLoading()
          this.swalService.present(result.message, "success", ((res: any) => {
            this.navCtrl.back();
          }));
        } else {
          this.httpService.handlingHttpError(result);
        }
      });
  }
}
