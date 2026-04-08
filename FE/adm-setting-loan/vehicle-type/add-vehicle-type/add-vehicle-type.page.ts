import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NavController } from '@ionic/angular';
import { HttpService } from 'src/app/services/http/http.service';
import { MainService } from 'src/app/services/main/main.service';
import { SwalService } from 'src/app/services/swal/swal.service';

@Component({
  selector: 'app-add-vehicle-type',
  templateUrl: './add-vehicle-type.page.html',
  styleUrls: ['./add-vehicle-type.page.scss'],
})
export class AddVehicleTypePage {

  id: any;
  canRefresh: boolean = false;

  vehicleType: any = {
    name: null,
    description: null,
    startDate: null,
    endDate: null,
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
    })
        if (this.id != null) {
          this.getData();
        }
  }


  doRefresh(event: any) {
    this.vehicleType = {
      name: null,
      description: null,
      startDate: null,
      endDate: null,
    }

    if (this.id != null) {
      this.getData();
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

  getData() {
    let urlApi = this.mainService.getMainUrl() + '/api/loan/vehicle-type/detail?id=' + this.id;

    this.httpService.getUrlApi(
      urlApi,
      false,
      (result: any, status: string) => {
        if (status === 'success') {
          if (result != null && result != '') {
            result.startDate = this.mainService.convertDate(result.startDate, this.mainService.globalConstant.dateFormatToDB);
            result.endDate = this.mainService.convertDate(result.endDate, this.mainService.globalConstant.dateFormatToDB);
            this.vehicleType = result;
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
        if (this.verificationForm(this.vehicleType)) {
          let urlApi = this.mainService.getMainUrl() + '/api/loan/vehicle-type';

          let dataPost: any = {};

          dataPost = this.vehicleType;
          dataPost.startDate = this.mainService.convertDate(this.vehicleType.startDate, this.mainService.globalConstant.dateFormatToPost);
          dataPost.endDate = this.mainService.convertDate(this.vehicleType.endDate, this.mainService.globalConstant.dateFormatToPost);

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
    if (type == 'start' && !this.vehicleType.startDate) {
      this.vehicleType.startDate = this.mainService.convertDate(new Date(), 'yyyy-MM-dd');
    } else if (type == 'end' && !this.vehicleType.endDate) {
      this.vehicleType.endDate = this.mainService.convertDate(new Date(), 'yyyy-MM-dd');
    }
  }
}
