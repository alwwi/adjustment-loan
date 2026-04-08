import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NavController } from '@ionic/angular';
import { HttpService } from 'src/app/services/http/http.service';
import { MainService } from 'src/app/services/main/main.service';
import { SwalService } from 'src/app/services/swal/swal.service';

@Component({
  selector: 'app-rating-model-matrix',
  templateUrl: './rating-model-matrix.page.html',
  styleUrls: ['./rating-model-matrix.page.scss'],
})
export class RatingModelMatrixPage {

  id: any;
  ratingModelId: any;

  canRefresh: boolean = false;
  listLookupResult: any = [];
  listRating: any = [];

  matrix: any = {
    paRatingId1: null,
    paRatingId2: null,
    result: null,
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
      this.ratingModelId = params["ratingModelId"];
    })
        if (this.id != null && this.id != "") {
          this.getData();
        }
        this.getLookupResult();
        this.getRating();
  }


  doRefresh(event: any) {
    this.matrix = {
      paRatingId1: null,
      paRatingId2: null,
      result: null,
    }
    if (this.id != null && this.id != "") {
      this.getData();
    }
    this.getLookupResult();
    this.getRating();
    setTimeout(() => {
      event.target.complete();
    }, 500);
  }

  getData() {
    let urlApi = this.mainService.getMainUrl() + '/api/loan/rating-model-matrix/detail?id=' + this.id;

    this.httpService.getUrlApi(
      urlApi,
      false,
      (result: any, status: string) => {
        if (status === 'success') {
          if (result != null && result != '') {
            this.matrix = result;
          }
        } else {
          this.navCtrl.back();
          this.httpService.handlingHttpError(result);
        }
      });
  }

  getLookupResult() {
    let urlApi = this.mainService.getMainUrl() + '/api/loan/rating-model-matrix/lookup-result';

    this.httpService.getUrlApi(
      urlApi,
      false,
      (result: any, status: string) => {
        if (status === 'success') {
          if (result != null && result != '') {
            this.listLookupResult = result;
          }
        } else {
          this.navCtrl.back();
          this.httpService.handlingHttpError(result);
        }
      });
  }

  getRating() {
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

  onSubmit() {
    this.swalService.present("Are you sure to submit?", "question", (res: any) => {
      if (res) {
        let urlApi = this.mainService.getMainUrl() + '/api/loan/rating-model-matrix';

        let dataPost: any = {};

        dataPost.loanRatingModelId = this.ratingModelId;
        dataPost.paRatingId1 = this.matrix.paRatingId1;
        dataPost.paRatingId2 = this.matrix.paRatingId2;
        dataPost.result = this.matrix.result;

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
      }
    });
  }

  handleSuccessSubmit(data: any) {
    this.mainService.dismissLoading();
    this.swalService.present(data.message, "success");
    this.navCtrl.back();
  }
}
