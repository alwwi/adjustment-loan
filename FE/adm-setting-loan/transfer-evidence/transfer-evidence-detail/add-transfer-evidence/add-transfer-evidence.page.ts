import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Chooser } from '@awesome-cordova-plugins/chooser/ngx';
import { NavController } from '@ionic/angular';
import { HttpService } from 'src/app/services/http/http.service';
import { MainService } from 'src/app/services/main/main.service';
import { SwalService } from 'src/app/services/swal/swal.service';

@Component({
  selector: 'app-add-transfer-evidence',
  templateUrl: './add-transfer-evidence.page.html',
  styleUrls: ['./add-transfer-evidence.page.scss'],
})
export class AddTransferEvidencePage {

  requestNo: any;
  transferDate: any;
  remark: any;
  messageValidation: string = "";
  nameFile: any = "No file choosen";
  dataFile: any = null;
  acceptType: string = "image/*,.pdf,.doc,.docx,application/msword, application/vnd.openxmlformats-officedocument.wordprocessingml.document,.csv, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms - excel,.ppt, .pptx";
  fileType: any;

  constructor(
    private activatedRoute: ActivatedRoute,
    public mainService: MainService,
    private httpService: HttpService,
    private swalService: SwalService,
    private navCtrl: NavController,
    private chooser: Chooser
  ) { }

  ionViewWillEnter() {
    this.activatedRoute.queryParams.subscribe((params) => {
      this.requestNo = this.mainService.decrypt(params["requestNo"]);
    });
  }

  async uploadFile(event?: any) {
    if (event === undefined) {
      this.chooser.getFile(this.acceptType).then((file) => {
        if (file == undefined) {
          return;
        }

        let tempFiles = new File([file.data], file.name, {
          type: file.mediaType,
          lastModified: new Date().getTime(),
        });
        let tempSize = (tempFiles.size / 1000000).toFixed(1);
        if (parseInt(tempSize) < 5.0) {

          // if (file.size < 5.0) {
          this.nameFile = file.name;
          this.fileType = file.mediaType;
          this.mainService.convertObjectUrlToBase64(file.dataURI)
            .then((res: any) => {
              if (res != undefined) {
                let arrDocs = res.split(",");
                this.dataFile = arrDocs[1];
              }
            })
        } else {
          this.swalService.present(
            "Maximum File Size Allowed is 5MB",
            "error",
          );
        }
      });
    } else {
      for (let data of event.currentFiles) {
        let tempSize = (data.size / 1000000).toFixed(1);
        if (parseInt(tempSize) < 5.0) {
          if (data.objectURL) {
            this.mainService.convertObjectUrlToBase64(data.objectURL.changingThisBreaksApplicationSecurity)
              .then((res: any) => {
                if (res != undefined) {
                  let arrDocs = res.split(",");
                  this.dataFile = arrDocs[1];
                }
              })
          } else {
            const dataUrl = await this.mainService.readFileAsDataURL(event.files[0]);
            let arrDocs = dataUrl.split(",");
            this.dataFile = arrDocs[1];
          }

          this.fileType = data.type;
          this.nameFile = data.name;
        } else {
          this.swalService.present(
            "Maximum File Size Allowed is 5MB",
            "error",
          );
        }
      }
    }
  }

  onSubmit() {
    this.swalService.present("Are you sure to submit?", "question", (res: any) => {
      if (res) {
        let urlApi = this.mainService.getMainUrl() + '/api/loan/transfer-evidence';

        let dataPost: any = {};

        dataPost.attachment = this.dataFile;
        dataPost.requestNo = this.requestNo;
        dataPost.remark = this.remark;
        dataPost.transferDate = this.mainService.convertDate(this.transferDate, this.mainService.globalConstant.dateFormatToPost);

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

  onChangeDate() {
    if (!this.transferDate) {
      this.transferDate = this.mainService.convertDate(new Date(), 'yyyy-MM-dd');
    }
  }
}
