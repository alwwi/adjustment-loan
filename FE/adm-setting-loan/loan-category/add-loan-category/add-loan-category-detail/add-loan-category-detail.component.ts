import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { HttpService } from 'src/app/services/http/http.service';
import { MainService } from 'src/app/services/main/main.service';
import { SwalService } from 'src/app/services/swal/swal.service';

@Component({
  selector: 'app-add-loan-category-detail',
  templateUrl: './add-loan-category-detail.component.html',
  styleUrls: ['./add-loan-category-detail.component.scss'],
})
export class AddLoanCategoryDetailComponent implements OnInit {

  id: any;
  categoryId: any;
  type: any;

  dataDetail: any = {};
  listVehicleType: any = [];
  listRatingModel: any = [];
  listElementName: any = [];
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

  listValueSource: any = [
    {
      title: "-- Select --",
      value: "",
    },
    {
      title: "Monthly Installment",
      value: "Monthly Installment",
    },
    {
      title: "Monthly Installment MOP",
      value: "Monthly Installment MOP",
    },
    {
      title: "Company Percentage",
      value: "Company Percentage",
    },
    {
      title: "Employee Percentage",
      value: "Employee Percentage",
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
    if (this.id != null) {
      this.getData();
    }

    if (this.type == "Vehicle Brand") {
      this.getVehicleType();
    } else if (this.type == "Plafond Bracket" || this.type === "Allowance Bracket") {
      this.getRatingModel();
      this.getElementName();
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

    let empPercentage = this.dataDetail.employeePercentage != null && this.dataDetail.employeePercentage != "" && this.dataDetail.employeePercentage != undefined ? parseInt(this.dataDetail.employeePercentage) : 0;
    let companyPercentage = this.dataDetail.companyPercentage != null && this.dataDetail.companyPercentage != "" && this.dataDetail.companyPercentage != undefined ? parseInt(this.dataDetail.companyPercentage) : 0;
    let val = companyPercentage + empPercentage;

    if (val > 100) {
      this.maxCalcPercentage = true;
    } else {
      this.maxCalcPercentage = false;
    }

    value = value.replace(/\D/g, "").replace("^[1-9][0-9]*$", "");
    event.target.value = value;
  }

  toggleChange(event: any) {
    this.dataDetail.active = event.detail.checked;
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

  getVehicleType() {
    let urlApi = this.mainService.getMainUrl() + '/api/loan/vehicle-type';

    this.httpService.getUrlApi(
      urlApi,
      false,
      (result: any, status: string) => {
        if (status === 'success') {
          if (result != null && result != '') {
            this.listVehicleType = result.content;
          }
        } else {
          this.close();
          this.httpService.handlingHttpError(result);
        }
      });
  }

  getRatingModel() {
    let urlApi = this.mainService.getMainUrl() + '/api/loan/rating-model?loanCategoryId=' + this.categoryId;

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

  getElementName() {
    let urlApi = this.mainService.getMainUrl() + '/api/loan/plafond-bracket/pick-element';

    this.httpService.getUrlApi(
      urlApi,
      false,
      (result: any, status: string) => {
        if (status === 'success') {
          if (result != null && result != '') {
            this.listElementName = result;
          }
        } else {
          this.close();
          this.httpService.handlingHttpError(result);
        }
      });
  }

  getData() {
    let urlApi = this.mainService.getMainUrl() + '/api/loan';

    if (this.type == 'Vehicle Brand') {
      urlApi += '/vehicle-brand';
    } else if (this.type == 'Plafond Bracket') {
      urlApi += '/plafond-bracket';
    } else if (this.type == 'Allowance Bracket') {
      urlApi += '/allowance-bracket';
    } else if (this.type == 'Feedback Fleet') {
      urlApi += '/feedback-fleet';
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

            if (this.type === 'Allowance Bracket') {
              this.dataDetail.maxValue = result.value;
            }

            if (this.dataDetail.minValue != null) {
              this.dataDetail.minValue = `${this.dataDetail.minValue}`.replace(/\D/g, "").replace(/\B(?=(\d{3})+(?!\d))/g, ".")
            }

            if (this.dataDetail.maxValue != null) {
              this.dataDetail.maxValue = `${this.dataDetail.maxValue}`.replace(/\D/g, "").replace(/\B(?=(\d{3})+(?!\d))/g, ".")
            }

            if (result.position != null) {
              this.dataDetail.positionName = result.position.name;
            }

            if (result.positionLevel != null) {
              this.dataDetail.positionLevelName = result.positionLevel.name;
            }

            if (result.organization != null) {
              this.dataDetail.organizationName = result.organization.name;
            }

            if (result.organizationGroup != null) {
              this.dataDetail.orgGroup = result.organizationGroup.name;
            }

            if (result.jobTitle != null) {
              this.dataDetail.jobTitleName = result.jobTitle.name;
            }

            if (result.jobFamily != null) {
              this.dataDetail.jobFamilyName = result.jobFamily.name;
            }

            if (result.grade != null) {
              this.dataDetail.gradeName = result.grade.name;
            }

            if (result.companyOffice != null) {
              this.dataDetail.companyOfficeName = result.companyOffice.name;
            }
          }
        } else {
          this.close();
          this.httpService.handlingHttpError(result);
        }
      });
  }

  onSubmit() {
    this.swalService.present("Are you sure to submit?", "question", (res: any) => {
      if (res) {
        let urlApi = this.mainService.getMainUrl() + '/api/loan';

        let dataPost: any = {};

        dataPost = this.dataDetail;
        dataPost.loanCategoryId = this.categoryId;
        dataPost.startDate = this.mainService.convertDate(this.dataDetail.startDate, this.mainService.globalConstant.dateFormatToPost);
        dataPost.endDate = this.mainService.convertDate(this.dataDetail.endDate, this.mainService.globalConstant.dateFormatToPost);

        if (this.type == 'Vehicle Brand') {
          dataPost.loanVehicleTypeId = this.dataDetail.vehicleTypeId;
          dataPost.engineType = this.dataDetail.engineType == "" && this.dataDetail.engineType == null ? null : this.dataDetail.engineType;
          dataPost.companyPercentage = this.dataDetail.companyPercentage;
          dataPost.employeePercentage = this.dataDetail.employeePercentage;

          urlApi += '/vehicle-brand';
        } else if (this.type == 'Plafond Bracket') {
          dataPost.elementName = this.dataDetail.elementName;
          dataPost.organizationId = this.dataDetail.organizationId;
          dataPost.organizationGroupId = this.dataDetail.orgGroupId;
          dataPost.positionId = this.dataDetail.chgPositionId;
          dataPost.positionLevelId = this.dataDetail.positionLevelId;
          dataPost.jobTitleId = this.dataDetail.jobTitleId;
          dataPost.jobFamilyId = this.dataDetail.jobFamilyId;
          dataPost.workLocationType = this.dataDetail.workLocationType == "" ? null : this.dataDetail.workLocationType;
          dataPost.companyOfficeId = this.dataDetail.companyOfficeId;
          dataPost.employmentStatus = this.dataDetail.employmentStatus == "" ? null : this.dataDetail.employmentStatus;
          dataPost.companyPercentage = this.dataDetail.companyPercentage;
          dataPost.employeePercentage = this.dataDetail.employeePercentage;
          dataPost.gradeId = this.dataDetail.gradeId;
          dataPost.minValue = this.dataDetail.minValue == undefined || this.dataDetail.minValue == "" ? null : this.dataDetail.minValue.replaceAll(".", "");
          dataPost.maxValue = this.dataDetail.maxValue == undefined || this.dataDetail.maxValue == "" ? null : this.dataDetail.maxValue.replaceAll(".", "");
          dataPost.multiplier = this.dataDetail.multiplier != "" && this.dataDetail.multiplier != undefined ? this.dataDetail.multiplier : null;
          dataPost.ratingId = this.dataDetail.ratingId;

          urlApi += '/plafond-bracket';
        } else if (this.type == 'Allowance Bracket') {
          dataPost.elementName = this.dataDetail.elementName;
          dataPost.syncElementName = this.dataDetail.syncElementName == "" ? null : this.dataDetail.syncElementName;
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
          dataPost.value = this.dataDetail.maxValue == undefined || this.dataDetail.maxValue == "" ? null : this.dataDetail.maxValue.replaceAll(".", "");
          dataPost.multiplier = this.dataDetail.multiplier != "" && this.dataDetail.multiplier != undefined ? this.dataDetail.multiplier : null;
          dataPost.ratingId = this.dataDetail.ratingId;

          urlApi += '/allowance-bracket';
        } else if (this.type == 'Feedback Fleet') {
          dataPost.syncElementName = this.dataDetail.syncElementName == "" ? null : this.dataDetail.syncElementName;
          dataPost.active = this.dataDetail.active;
          dataPost.valueSource = this.dataDetail.valueSource == "" && this.dataDetail.valueSource == null ? null : this.dataDetail.valueSource;
          dataPost.sequenceNo = this.dataDetail.sequenceNo == "" && this.dataDetail.sequenceNo == undefined ? null : this.dataDetail.sequenceNo;
          urlApi += '/feedback-fleet';
        }

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
