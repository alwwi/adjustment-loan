import { Component } from '@angular/core';
import { ActivatedRoute, NavigationExtras } from '@angular/router';
import { NavController } from '@ionic/angular';
import { HttpService } from 'src/app/services/http/http.service';
import { MainService } from 'src/app/services/main/main.service';

@Component({
  selector: 'app-transfer-evidence-detail',
  templateUrl: './transfer-evidence-detail.page.html',
  styleUrls: ['./transfer-evidence-detail.page.scss'],
})
export class TransferEvidenceDetailPage {

  id: any;
  canRefresh: boolean = false;
  isSkeletonLoading: boolean = true;

  transfer: any;
  messageValidation: string = "";

  constructor(
    private activatedRoute: ActivatedRoute,
    public mainService: MainService,
    private httpService: HttpService,
    private navCtrl: NavController
  ) { }

  ionViewWillEnter() {
    this.activatedRoute.queryParams.subscribe((params) => {
      this.id = this.mainService.decrypt(params["id"]);
    });
        this.getData();
  }


  doRefresh(event: any) {
    this.transfer = {};
    this.isSkeletonLoading = true;
    this.getData();
    setTimeout(() => {
      event.target.complete();
    }, 500);
  }

  downloadDocs(url: any) {
    this.mainService.openInAppBrowser(encodeURI(url));
  }

  getData() {
    let urlApi = this.mainService.getMainUrl() + '/api/loan/transfer-evidence/loan/' + this.id;

    this.httpService.getUrlApi(
      urlApi,
      false,
      (result: any, status: string) => {
        if (status === 'success') {
          if (result != null && result != '') {
            this.transfer = result;
          }
        } else {
          this.navCtrl.back();
          this.httpService.handlingHttpError(result);
        }
        this.isSkeletonLoading = false;
      });
  }

  uploadEnvidence() {
    let params: NavigationExtras = {
      queryParams: {
        requestNo: this.mainService.encrypt(this.transfer.requestNo),
      },
    };

    this.navCtrl.navigateForward("/administration/settings/loan/transfer-evidence/transfer-evidence-detail/add-transfer-evidence", params);
  }
}
