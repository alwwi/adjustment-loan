import { Component } from '@angular/core';
import { NavController } from '@ionic/angular';
import { HttpService } from 'src/app/services/http/http.service';
import { MainService } from 'src/app/services/main/main.service';
import { SwalService } from 'src/app/services/swal/swal.service';

@Component({
  selector: 'app-add-sync-loan',
  templateUrl: './add-sync-loan.page.html',
  styleUrls: ['./add-sync-loan.page.scss'],
})
export class AddSyncLoanPage {

  id: any;
  canRefresh: boolean = false;

  syncLoan: any = {
    paymentDate: null,
    startDate: null,
    endDate: null,
  };
  messageValidation: string = "";

  constructor(
    public mainService: MainService,
    private httpService: HttpService,
    private swalService: SwalService,
    private navCtrl: NavController
  ) { }

  ionViewWillEnter() {
  }


  doRefresh(event: any) {
    this.syncLoan = {
      paymentDate: null,
      startDate: null,
      endDate: null,
    }

    setTimeout(() => {
      event.target.complete();
    }, 500);
  }

  verificationForm(form: any) {
    const startDate = this.mainService.convertDate(form.startDate, this.mainService.globalConstant.dateFormatToDB);
    const endDate = this.mainService.convertDate(form.endDate, this.mainService.globalConstant.dateFormatToDB);

    if (startDate > endDate) {
      this.messageValidation = "End Date must be greater than Start Date";
      return false;
    }

    return true;
  }

  onSubmit() {
    this.swalService.present("Are you sure to submit?", "question", (res: any) => {
      if (res) {
        if (this.verificationForm(this.syncLoan)) {
          let urlApi = this.mainService.getMainUrl() + '/api/admin/sync/loan/submit';

          let dataPost: any = {};

          dataPost.paymentStartDate = this.mainService.convertDate(this.syncLoan.startDate, this.mainService.globalConstant.dateFormatToDB);
          dataPost.paymentEndDate = this.mainService.convertDate(this.syncLoan.endDate, this.mainService.globalConstant.dateFormatToDB);
          dataPost.paymentDate = this.mainService.convertDate(this.syncLoan.paymentDate, this.mainService.globalConstant.dateFormatToMonth);

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
    this.navCtrl.back();
  }

  onChangeDate(type: string) {
    if (type == 'start' && !this.syncLoan.startDate) {
      this.syncLoan.startDate = this.mainService.convertDate(new Date(), 'yyyy-MM-dd');
    } else if (type == 'end' && !this.syncLoan.endDate) {
      this.syncLoan.endDate = this.mainService.convertDate(new Date(), 'yyyy-MM-dd');
    } else if (type == 'payment' && !this.syncLoan.paymentDate) {
      this.syncLoan.paymentDate = this.mainService.convertDate(new Date(), 'yyyy-MM-dd');
    }
  }
}
