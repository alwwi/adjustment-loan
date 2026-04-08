import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Chooser } from '@awesome-cordova-plugins/chooser/ngx';
import { NavController } from '@ionic/angular';
import { HttpService } from 'src/app/services/http/http.service';
import { MainService } from 'src/app/services/main/main.service';
import { NotificationService } from 'src/app/services/notification/notification.service';
import { SwalService } from 'src/app/services/swal/swal.service';

@Component({
  selector: 'app-loan',
  templateUrl: './loan.page.html',
  styleUrls: ['./loan.page.scss'],
})
export class LoanPage {
  page: number = 1;
  categories: any = [];
  category: any = null;
  loanCategoryId: any = null;
  loanTypes: any = [];
  loanRequirements: any = [];
  loanType: any = null;
  loanTypeId: any = null;
  vehicleType: any = [];
  vehicleTypeId: any = null;
  brandType: any = [];
  brandTypeId: any = null;
  brandTypeName: any = null;
  attachmentList: any = [];
  minTenor: any = 0;
  maxTenor: any = 0;
  isVehicle: boolean = false;
  isDP: boolean = false;
  hasSimulation: boolean = false;
  remark: any;
  loanDate: any;
  amount: any;
  tenor: any;
  downPayment: any;
  loanAmount: any;
  interestRate: any;
  monthlyInstallment: any;

  messageValidation: string = "";
  acceptType: string = "image/*,application/pdf";

  isAdmin: boolean = false;
  isSkeletonLoading: boolean = true;
  employee: any;
  employeeNo: any;
  employmentId: any;
  dateerer: any;
  isEmpty: boolean = true;
  isCap: boolean = false;
  isMap: boolean = false;

  constructor(
    private httpService: HttpService,
    public mainService: MainService,
    private route: ActivatedRoute,
    private navCtrl: NavController,
    private chooser: Chooser,
    private swalService: SwalService,
    private notificationService: NotificationService
  ) { }

  ionViewWillEnter() {
    this.loanDate = this.mainService.convertDate(new Date(), this.mainService.globalConstant.dateFormatToDB);;
    this.route.queryParams.subscribe((params: any) => {
      this.isAdmin = params["isAdmin"] == "true";
      this.employeeNo = this.mainService.decrypt(params["employeeNo"]);
      this.employmentId = this.mainService.decrypt(params["employmentId"]);
    })
    this.getCategory();
    if (this.isAdmin) {
      this.isSkeletonLoading = true;
      this.getSelectedEmplDetail();
    }
  }

  getCategory() {
    let urlApi = this.mainService.getMainUrl() + '/api/loan/category';

    this.httpService.getUrlApi(urlApi, false, (res: any, status: string) => {
      if (status === 'success') {
        this.handleGetCategory(res);
      } else {
        this.httpService.handlingHttpError(res);
      }
    });
  }

  handleGetCategory(res: any) {
    if (res.content.length > 0) {
      this.categories = res.content;
    } else {
      this.categories = [];
    }
  }

  getLoanType() {
    let urlApi = this.mainService.getMainUrl() + '/api/loan/type?loanCategoryId=' + this.loanCategoryId;

    this.httpService.getUrlApi(urlApi, false, (res: any, status: string) => {
      if (status === 'success') {
        this.handleLoanType(res);
      } else {
        this.httpService.handlingHttpError(res);
      }
    });
  }

  handleLoanType(res: any) {
    if (res.content.length > 0) {
      this.loanTypes = res.content;
    } else {
      this.loanTypes = [];
    }
  }

  getVehicleType() {
    let urlApi = this.mainService.getMainUrl() + '/api/loan/vehicle-type/by-loan-category?loanCategoryId=' + this.loanCategoryId;

    this.httpService.getUrlApi(urlApi, false, (res: any, status: string) => {
      if (status === 'success') {
        this.handleVehicleType(res);
      } else {
        this.httpService.handlingHttpError(res);
      }
    });
  }

  handleVehicleType(res: any) {
    if (res.length > 0) {
      this.vehicleType = res;
    } else {
      this.vehicleType = [];
    }
  }

  // getVehicleBrand() {
  //   let urlApi = this.mainService.getMainUrl() + '/api/loan/vehicle-brand?loanCategoryId=' + this.loanCategoryId + '&vehicleTypeId=' + this.vehicleTypeId;

  //   this.httpService.getUrlApi(urlApi, false, (res: any, status: string) => {
  //     if (status === 'success') {
  //       this.handleVehicleBrand(res);
  //     } else {
  //       this.httpService.handlingHttpError(res);
  //     }
  //   });
  // }

  // handleVehicleBrand(res: any) {
  //   if (res.content.length > 0) {
  //     this.brandType = res.content;
  //   } else {
  //     this.brandType = [];
  //   }
  // }

  openSearchMenu(type: string) {
    let data: any = {
      searchType: type,
      loanCategoryId: this.loanCategoryId,
      vehicleTypeId: this.vehicleTypeId
    }

    this.mainService.searchModal(data, (res: any) => {
      if (res.data != undefined) {
        this.brandTypeName = res.data.name;
        this.brandTypeId = res.data.id;
      }
    });
  }

  getListAttachment() {
    let urlApi = this.mainService.getMainUrl() + '/api/loan/attachment?loanTypeId=' + this.loanTypeId;

    this.httpService.getUrlApi(urlApi, false, (res: any, status: string) => {
      if (status === 'success') {
        this.handleGetListAttachment(res);
      } else {
        this.httpService.handlingHttpError(res);
      }
    });
  }

  handleGetListAttachment(res: any) {
    if (res.content.length > 0) {
      this.attachmentList = res.content;
      this.attachmentList.forEach((el: any) => {
        el.fileName = "No file choosen";
        el.fileData = null;
      });
    } else {
      this.attachmentList = [];
    }
  }

  getSelectedEmplDetail() {
    const urlApi = this.mainService.getMainUrl() + '/api/admin/employee/find/detail?q='
      + this.employeeNo;

    this.httpService.getUrlApi(urlApi, false, (res: any, status: string) => {
      if (status === 'success') {
        this.handleSuccessEmplDetail(res);
      } else {
        this.httpService.handlingHttpError(res);
      }
      this.isSkeletonLoading = false;
    });
  }

  handleSuccessEmplDetail(data: any) {
    if (data != null && data.content.length > 0) {
      this.employee = data.content[0];
    }
    this.isSkeletonLoading = false;
  }

  checkLoanAmount() {
    let urlApi = this.mainService.getMainUrl() + '/api/user/dataApproval/personal/loan/check-eligibility-loan?loanAmount=' + this.amount.replaceAll(".", "")
      + '&loanTenor=' + this.tenor + '&loanCategoryId=' + this.loanCategoryId + '&loanTypeId=' + this.loanTypeId;
    if (this.downPayment != "" && this.downPayment != undefined) {
      urlApi += '&downPayment=' + this.downPayment.replaceAll(".", "");
    } else {
      urlApi += '&downPayment=0';
    }

    this.httpService.getUrlApi(urlApi, true, (res: any, status: string) => {
      if (status === 'success') {
        this.handleSuccessCheckAmount(res);
      } else {
        this.httpService.handlingHttpError(res);
        this.mainService.dismissLoading();
      }
    });
  }

  handleSuccessCheckAmount(res: any) {
    if (res != null) {
      if (res.eligible) {
        this.hasSimulation = res.hasSimulation == true;
        if (res.hasSimulation) {
          this.page++;
          this.interestRate = res.simulation.interestRate;
          this.monthlyInstallment = res.simulation.monthlyInstallment;
          this.loanAmount = res.simulation.loanAmount;
        } else {
          this.page += 2;
          this.loanAmount = res.simulation.loanAmount;
        }
      } else {
        this.swalService.present("Your  amount not eligible request loan", "error")
      }
    }
    this.mainService.dismissLoading();
  }

  checkMonthlyInstallment() {
    let urlApi = this.mainService.getMainUrl() + '/api/user/dataApproval/personal/loan/check-dsr-loan?monthlyInstallment='
      + this.employmentId + this.monthlyInstallment + '&loanTypeId=' + this.loanTypeId;

    this.httpService.getUrlApi(urlApi, false, (res: any, status: string) => {
      if (status === 'success') {
        this.handleSuccessMonthlyInstallment(res);
      } else {
        this.httpService.handlingHttpError(res);
      }
    });
  }

  handleSuccessMonthlyInstallment(res: any) {
    if (res != null) {
      if (res.eligible) {
        this.page++;
      } else {
        this.swalService.present("Your  monthly installment not eligible request loan", "error");
      }
    }
  }

  changeSelect(event: string) {
    if (event == "Category") {
      this.vehicleType = null;
      this.brandType = null;
      this.loanType = null;
      this.tenor = null;
      this.isCap = false;
      this.isMap = false;
      this.isEmpty = true;
      this.loanCategoryId = this.category.id;
      this.isVehicle = this.category.vehicle;
      this.getLoanType();
      this.getVehicleType();
    } else if (event == "Loan Type") {
      if (this.loanType != null) {
        this.loanTypeId = this.loanType.id;
        this.minTenor = this.loanType.minTenor;
        this.maxTenor = this.loanType.maxTenor;
        if(this.minTenor === this.maxTenor && this.minTenor > 0){
          this.tenor = this.minTenor;
        } else{
          this.tenor = null;
        }
        this.isDP = this.loanType.downPayment == true;
        this.isCap = this.loanType.name == 'CAP';
        this.isMap = this.loanType.name == 'MAP';
        this.getRequirements();
      }
      this.getListAttachment();
    } else if (event == "Vehicle Type") {
      this.minTenor = this.loanType.minTenor;
      this.maxTenor = this.loanType.maxTenor;
      // this.getVehicleBrand()

      if(this.minTenor === this.maxTenor && this.minTenor > 0){
        this.tenor = this.minTenor;
      }
    }

  }

  changeTenor(event: any) {
    let value = event.target.value;

    if(value === '' || value === null || value === undefined){
      this.tenor = null;
      event.target.value = '';
      return;
    }

    if (value >= this.maxTenor) {
      value = this.maxTenor;
      event.target.value = value;
    } else if (value <= this.minTenor) {
      value = this.minTenor;
      event.target.value = value;
    } else {
      event.target.value = value;
    }
    this.tenor = value;
  }

  changeAmount(event: any) {
    let value = event.target.value;
    value = value.replace(/\D/g, "").replace(/\B(?=(\d{3})+(?!\d))/g, ".");
    event.target.value = value;
  };

  changeAmountBack(val: any) {
    let value = val;
    value = value.replace(/\D/g, "").replace(/\B(?=(\d{3})+(?!\d))/g, ".");
    return value;
  }

  loadPrevLoan() {
    if (this.page == 2) {
      this.amount = this.changeAmountBack(this.amount);
    }
    if (this.hasSimulation) {
      this.page--;
    } else {
      this.page -= 2;
    }
  }

  loadNextLoan() {
    if (this.page == 2) {
      this.page++;
    } else if (this.page == 1) {
      this.checkLoanAmount();
    }
  }

  verificationForm() {
    let list = [];
    let count = 0;
    this.attachmentList.forEach((el: any) => {
      if (el.mandatory) {
        count++;
        if (el.fileData != null) {
          list.push(el);
        }
      }
    });

    if (list.length != count) {
      return true;
    }
    return false;
  }

  async uploadFile(event?: any, index?: any) {
    if (event === undefined || event === null) {
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
          this.mainService.convertObjectUrlToBase64(file.dataURI)
            .then((res: any) => {
              if (res != undefined) {
                let arrDocs = res.split(",");
                this.attachmentList[index].fileData = arrDocs[1];
                this.attachmentList[index].fileName = file.name;
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
                  this.attachmentList[index].fileData = arrDocs[1];
                  this.attachmentList[index].fileName = data.name;
                }
              })
          } else {
            const dataUrl = await this.mainService.readFileAsDataURL(event.files[0]);
            let arrDocs = dataUrl.split(",");
            this.attachmentList[index].fileData = arrDocs[1];
            this.attachmentList[index].fileName = data.name;
          }
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
        let urlApi = this.mainService.getMainUrl();

        if (this.isAdmin) {
          urlApi += '/api/admin/dataApproval/loan/submit';
        } else {
          urlApi += '/api/user/dataApproval/personal/loan/submit';
        }

        let dataPost: any = {};

        dataPost.employmentId = this.isAdmin ? this.employmentId : null;
        dataPost.loanCategoryId = this.loanCategoryId;
        dataPost.actionRequest = this.category.name;
        dataPost.loanDate = this.mainService.convertDate(this.loanDate, this.mainService.globalConstant.dateFormatToPost);
        dataPost.loanTypeId = this.loanTypeId;
        if (this.brandType != null) {
          dataPost.brandId = this.brandTypeId;
        }
        dataPost.amountOtr = this.amount.replaceAll(".", "");
        dataPost.amount = this.loanAmount;
        dataPost.downPayment = this.downPayment == undefined || this.downPayment == "" ? null : this.downPayment.replaceAll(".", "");
        dataPost.monthlyInstallment = this.monthlyInstallment;
        dataPost.interestRate = this.interestRate;
        dataPost.tenor = this.tenor;
        dataPost.remark = this.remark;
        dataPost.bypassApproval = this.isAdmin ? true : null;
        dataPost.attachmentDetails = [];

        this.attachmentList.forEach((el: any) => {
          dataPost.attachmentDetails.push({
            loanAttachmentId: el.id,
            path: el.fileData
          });
        });

        if (this.isAdmin) {
          dataPost.employmentRequestee = this.employee.employmentId;
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
    if (this.isAdmin) {
      this.navCtrl.navigateBack('/administration/transactions');
    } else {
      let payload: any = {
        url: "/self-service/loan",
        contents: "Ada Pengajuan Loan baru. segera check di menu Approval",
        headings: "Pengajuan Request Loan"
      };
      this.notificationService.postPushNotification(data, payload);
      this.navCtrl.back();
    }
  }

  onChangeDate() {
    if (!this.loanDate) {
      this.loanDate = this.mainService.convertDate(new Date(), 'yyyy-MM-dd');
    }
  }

  getRequirements() {
    let urlApi = this.mainService.getMainUrl() + '/api/user/dataApproval/personal/loan/check-requirements-loan?loanCategoryId=' + this.loanCategoryId + '&loanTypeId=' + this.loanTypeId;

    this.httpService.getUrlApi(urlApi, false, (res: any, status: string) => {
      if (status === 'success') {
        this.handleRequirements(res);
      } else {
        this.httpService.handlingHttpError(res);
      }
    });
  }

  handleRequirements(res: any) {
    if (res) {
      this.loanRequirements = res;
      this.isEmpty = res.message == "Failed" ? true : false;
    } else {
      this.loanRequirements = [];
    }
  }
}
