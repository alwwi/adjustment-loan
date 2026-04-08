import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { HttpService } from 'src/app/services/http/http.service';
import { MainService } from 'src/app/services/main/main.service';
import { SwalService } from 'src/app/services/swal/swal.service';

@Component({
  selector: 'app-add-loan-type-detail',
  templateUrl: './add-loan-type-detail.component.html',
  styleUrls: ['./add-loan-type-detail.component.scss'],
})
export class AddLoanTypeDetailComponent implements OnInit {

  id: any;
  loanTypeId: any;
  loanCategoryId: any;
  type: any;

  dataDetail: any = {};
  listRatingModel: any = [];
  listDiscipline: any = [];
  maxCalcPercentage: boolean = false;

  messageValidation: string = "";

  listEngineType: any = [
    {
      title: "-- Select --",
      value: "",
    },
    {
      title: "Listrik",
      value: "Listrik",
    },
    {
      title: "Konvensional",
      value: "Konvensional",
    },
    {
      title: "Hybrid",
      value: "Hybrid",
    }
  ];

  listWorkLocationType: any = [
    {
      title: "-- Select --",
      value: "",
    },
    {
      title: "HO",
      value: "HO",
    },
    {
      title: "Branch",
      value: "Branch",
    },
    {
      title: "Fleet",
      value: "Fleet",
    },
    {
      title: "Regional",
      value: "Regional",
    },
    {
      title: "Satelite",
      value: "Satelite",
    },
    {
      title: "Pool",
      value: "Pool",
    },
    {
      title: "POS",
      value: "POS",
    }
  ];

  listEmploymentStatus: any = [
    {
      title: "-- Select --",
      value: "",
    },
    {
      title: "Permanent",
      value: "Permanent",
    },
    {
      title: "Contract 1",
      value: "Contract 1",
    },
    {
      title: "Contract 2",
      value: "Contract 2",
    },
    {
      title: "Probation",
      value: "Probation",
    },
    {
      title: "Trainee",
      value: "Trainee",
    },
    {
      title: "Out Source Head Count",
      value: "Out Source Head Count",
    },
    {
      title: "Out Source Non Head Count",
      value: "Out Source Non Head Count",
    },
  ];

  constructor(
    public mainService: MainService,
    private httpService: HttpService,
    private swalService: SwalService,
    private modalCtrl: ModalController
  ) { }

  ngOnInit() {
    if (this.id != null && this.id != "") {
      this.getData();
    }

    if (this.type == "Discipline") {
      this.getDiscipline();
    } else if (this.type == "Criteria") {
      this.getRatingModel();
    }
  }

  close() {
    this.modalCtrl.dismiss();
  }

  change(event: any) {
    let value = event.target.value;
    value = value.replace(/\D/g, "").replace("^[1-9][0-9]*$", "");
    event.target.value = value;
  }

  changeValue(event: any) {
    let value = event.target.value;
    value = value.replace(/\D/g, "").replace(/\B(?=(\d{3})+(?!\d))/g, ".");;
    event.target.value = value;
  }

  checkMaxValue(event: any) {
    let value = event.target.value;
    value = value.replace(/\D/g, "").replace("^[1-9][0-9]*$", "");

    if (value > 600) {
      value = "600";
    }
    event.target.value = value;
  }

  toggleChange(event: any) {
    this.dataDetail.mandatory = event.detail.checked;
  }

  openSearchMenu(type: string) {
    let data: any = {
      searchType: type,
    }

    this.mainService.searchModal(data, (res: any) => {
      if (res.data != undefined) {
        if (type == "Organization") {
          this.dataDetail.organizationName = res.data.name;
          this.dataDetail.organizationId = res.data.id;
        } else if (type == "Organization Group") {
          this.dataDetail.orgGroup = res.data.name;
          this.dataDetail.orgGroupId = res.data.id;
        } else if (type == "Position Job") {
          this.dataDetail.positionName = res.data.name;
          this.dataDetail.chgPositionId = res.data.id;
        } else if (type == "Position Level") {
          this.dataDetail.positionLevelName = res.data.name;
          this.dataDetail.positionLevelId = res.data.id;
        } else if (type == "Job Title") {
          this.dataDetail.jobTitleName = res.data.name;
          this.dataDetail.jobTitleId = res.data.id;
        } else if (type == "Job Family") {
          this.dataDetail.jobFamilyName = res.data.name;
          this.dataDetail.jobFamilyId = res.data.id;
        } else if (type == "Work Location") {
          this.dataDetail.companyOfficeName = res.data.name;
          this.dataDetail.companyOfficeId = res.data.id;
        } else if (type == "Grade") {
          this.dataDetail.gradeName = res.data.name;
          this.dataDetail.gradeId = res.data.id;
        }
      }
    });
  }

  getDiscipline() {
    let urlApi = this.mainService.getMainUrl() + '/api/user/discipline';

    this.httpService.getUrlApi(
      urlApi,
      false,
      (result: any, status: string) => {
        if (status === 'success') {
          if (result != null && result != '') {
            this.listDiscipline = result;
          }
        } else {
          this.close();
          this.httpService.handlingHttpError(result);
        }
      });
  }

  getRatingModel() {
    let urlApi = this.mainService.getMainUrl() + '/api/loan/rating-model?loanCategoryId=' + this.loanCategoryId;

    this.httpService.getUrlApi(
      urlApi,
      false,
      (result: any, status: string) => {
        if (status === 'success') {
          if (result != null && result != '') {
            this.listRatingModel = result.content;
          }
        } else {
          this.close();
          this.httpService.handlingHttpError(result);
        }
      });
  }

  getData() {
    let urlApi = this.mainService.getMainUrl() + '/api/loan';

    if (this.type == 'Attachment') {
      urlApi += '/attachment';
    } else if (this.type == 'Discipline') {
      urlApi += '/discipline';
    } else if (this.type == 'Criteria') {
      urlApi += '/criteria';
    }

    urlApi += '/detail?id=' + this.id;

    this.httpService.getUrlApi(
      urlApi,
      false,
      (result: any, status: string) => {
        if (status === 'success') {
          if (result != null && result != '') {
            result.startDate = this.mainService.convertDate(result.startDate, this.mainService.globalConstant.dateFormatToDB);
            result.endDate = this.mainService.convertDate(result.endDate, this.mainService.globalConstant.dateFormatToDB);
            this.dataDetail = result;
          }
        } else {
          this.close();
          this.httpService.handlingHttpError(result);
        }
      });
  }

  verificationForm(form: any) {
    let startDate = this.mainService.convertDate(form.startDate, this.mainService.globalConstant.dateFormatToDB);
    let endDate = this.mainService.convertDate(form.endDate, this.mainService.globalConstant.dateFormatToDB);
    if (endDate < startDate) {
      this.messageValidation = "Start Date Must be Lower than End Date";
      return false;
    }

    if (form.minTenor > form.maxTenor) {
      this.messageValidation = "Min Tenor Must be Lower than Max Tenor";
      return false;
    } else if (form.minTenor == 0 || form.maxTenor == 0) {
      this.messageValidation = "Tenor cannot be 0";
      return false;
    }
    return true;
  }

  onSubmit() {
    this.swalService.present("Are you sure to submit?", "question", (res: any) => {
      if (res) {
        if (this.verificationForm(this.dataDetail)) {
          let urlApi = this.mainService.getMainUrl() + '/api/loan';

          let dataPost: any = {};

          dataPost = this.dataDetail;
          dataPost.loanTypeId = this.loanTypeId;
          dataPost.startDate = this.mainService.convertDate(this.dataDetail.startDate, this.mainService.globalConstant.dateFormatToPost);
          dataPost.endDate = this.mainService.convertDate(this.dataDetail.endDate, this.mainService.globalConstant.dateFormatToPost);

          if (this.type == 'Discipline') {
            dataPost.disciplineId = this.dataDetail.discipline;
            urlApi += '/discipline';
          } else if (this.type == 'Criteria') {
            dataPost.name = this.dataDetail.name;
            dataPost.description = this.dataDetail.description;
            dataPost.organizationId = this.dataDetail.organizationId;
            dataPost.organizationGroupId = this.dataDetail.orgGroupId;
            dataPost.positionId = this.dataDetail.chgPositionId;
            dataPost.positionLevelId = this.dataDetail.positionLevelId;
            dataPost.jobTitleId = this.dataDetail.jobTitleId;
            dataPost.jobFamilyId = this.dataDetail.jobFamilyId;
            dataPost.workLocationType = this.dataDetail.workLocationType == "" ? null : this.dataDetail.workLocationType;
            dataPost.companyOfficeId = this.dataDetail.companyOfficeId;
            dataPost.employmentStatus = this.dataDetail.employmentStatus == "" ? null : this.dataDetail.employmentStatus;
            dataPost.gradeId = this.dataDetail.gradeId;
            dataPost.minLos = this.dataDetail.minLos;
            dataPost.maxLos = this.dataDetail.maxLos;
            dataPost.ratingId = this.dataDetail.ratingModelId;
            urlApi += '/criteria';
          } else if (this.type == 'Attachment') {
            dataPost.name = this.dataDetail.name;
            dataPost.description = this.dataDetail.description;
            dataPost.mandatory = this.dataDetail.mandatory;
            urlApi += '/attachment';
          }

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
    this.modalCtrl.dismiss(data.message, "success");
  }

  onChangeDate(type: string) {
    if (type == 'start' && !this.dataDetail.startDate) {
      this.dataDetail.startDate = this.mainService.convertDate(new Date(), 'yyyy-MM-dd');
    } else if (type == 'end' && !this.dataDetail.endDate) {
      this.dataDetail.endDate = this.mainService.convertDate(new Date(), 'yyyy-MM-dd');
    }
  }
}
