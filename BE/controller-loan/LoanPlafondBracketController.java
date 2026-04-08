package com.phincon.talents.app.controllers.api.loan;

import com.phincon.talents.app.dto.loan.ElementNameListDTO;
import com.phincon.talents.app.dto.loan.PlafondBracketDetailDTO;
import com.phincon.talents.app.dto.loan.PlafondBracketListDTO;
import com.phincon.talents.app.dto.loan.PlafondBracketPostDTO;
import com.phincon.talents.app.services.loan.LoanPlafondBracketService;
import com.phincon.talents.app.utils.CustomMessageWithId;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/loan/plafond-bracket")
public class LoanPlafondBracketController {
    private static final Logger log = LogManager.getLogger(LoanPlafondBracketController.class);

    @Autowired
    LoanPlafondBracketService service;

    String identifier = "Loan Plafon Bracket";


    @GetMapping()
    public Page<PlafondBracketListDTO> listData(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate,

            @RequestParam(value = "loanCategoryId", required = false) String loanCategoryId,

            @RequestParam(value = "page",required = false,defaultValue = "0") String page,
            @RequestParam(value = "size",required = false,defaultValue = "1000") String size
    ) {

        LocalDate startDateUsed=null;
        if(startDate == null || startDate.isEmpty()){
//            startDateUsed=LocalDate.now();
        }else{
            startDateUsed=LocalDate.parse(startDate);
        }

        LocalDate endDateUsed=null;
        if(endDate == null || endDate.isEmpty()){
//            endDateUsed=LocalDate.now();
        }else{
            endDateUsed=LocalDate.parse(startDate);
        }

        return service.listData(page,size,name, startDateUsed,endDateUsed,loanCategoryId);
    }

    @GetMapping("detail")
    public PlafondBracketDetailDTO getDetailEmployeeCertification(@RequestParam(value = "id") String id){
        return service.getDetail(identifier,id);
    }

    @PostMapping
    public ResponseEntity<CustomMessageWithId> submitData(@RequestBody PlafondBracketPostDTO request){
        log.info("============ submit " + identifier + " ============");
        ResponseEntity<CustomMessageWithId> response = service.submitData(identifier,request);
        log.info("============ submit " + identifier + " ============");
        return response;
    }

    @PostMapping("delete")
    public ResponseEntity<CustomMessageWithId> deleteCertification(@RequestBody PlafondBracketPostDTO request){
        log.info("============ delete " + identifier + " ============");
        ResponseEntity<CustomMessageWithId>  response= service.deleteData(identifier,request.getId());
        log.info("============ delete " + identifier + " ============");
        return response;
    }

    @GetMapping("pick-element")
    public List<ElementNameListDTO> getElementNameList(){
        List<ElementNameListDTO> elementList = new ArrayList<>();
        String[] elementListStr = new String[]{
                "Gaji Pokok",
                "Tunjangan Specialist",
                "Tunjangan Service",
                "Tunjangan Khusus",
                "Tunjangan Kemahalan",
                "Tunjangan Jabatan",
                "Tunjangan Transport",
                "Tunjangan CAP",
                "Tunjangan MAP",
                "Tunjangan Asuransi CAP",
                "Bantuan Penugasan",
                "Tunjangan Kehadiran",
                "Overtime",
                "Overtime Meal",
                "Tunjangan Lain - lain",
                "Honorarium",
                "Bantuan Tempat Tinggal",
                "Potongan Tempat Tinggal",
                "Bantuan Ekspedisi",
                "Potongan Ekspedisi",
                "Tunjangan Operasional CAP",
                "Potongan Operation CAP",
                "Bantuan Service CAP",
                "Potongan Service CAP",
                "Bantuan Anak Sekolah",
                "Potongan Bantuan Sekolah",
                "Premi Asuransi Kesehatan",
                "Potongan Premi Asuransi Kesehatan",
                "Potongan Pajak Regular",
                "Tunjangan Pajak Regular",
                "Potongan / Loan",
                "Potongan Pinjaman Pegawai",
                "Potongan Asuransi CAP",
                "Potongan Angsuran CAP",
                "Potongan Angsuran MAP",
                "Potongan Angsuran MOP Non Subsidi",
                "Potongan Angsuran Project HP",
                "Potongan Angsuran Balloon Payment CAP",
                "Potongan Koperasi - Pokok",
                "Potongan Iuran Wajib Pegawai BMRI",
                "Tunjangan BPJS TK - JKK",
                "Tunjangan BPJS TK - JKM",
                "Tunjangan BPJS TK - JHT Comp",
                "Potongan BPJS TK - JKK",
                "Potongan BPJS TK - JKM",
                "Potongan BPJS TK - JHT Comp",
                "Potongan BPJS TK - JHT Emp",
                "Tunjangan BPJS JP Comp",
                "Potongan BPJS JP Comp",
                "Potongan BPJS JP Emp",
                "Tunjangan BPJS Kesehatan Comp",
                "Potongan BPJS Kesehatan Comp",
                "Potongan BPJS Kesehatan Emp",
                "Tunjangan Bunga",
                "Incentive AR",
                "Reward Denda AR",
                "Incentive Sales BM",
                "Incentive Sales SH",
                "Incentive Sales SHD",
                "Incentive Sales SO",
                "Incentive Sales AM Fleet",
                "Incentive Sales RM Fleet",
                "Incentive Apresiasi",
                "Incentive BSM OTO",
                "Incentive CH",
                "Reward Tarikan AR",
                "Incentive ACP BM",
                "Incentive ACP SH",
                "Incentive ACP SHD",
                "Incentive ACP SO",
                "Incentive Konsistensi ACP BM",
                "Incentive GAP BM",
                "Incentive GAP SH",
                "Incentive GAP SHD",
                "Incentive GAP SO",
                "Incentive Konsistensi GAP BM",
                "Incentive Multiguna",
                "Incentive Refferall Multiguna",
                "Incentive Telemarketing",
                "Incentive HIC",
                "Incentive Crash Program HIC",
                "THR",
                "Bonus",
                "Santunan Duka",
                "Potongan Santunan Duka",
                "Potongan CUG",
                "Potongan Kartu Kredit",
                "Rapel Gaji Pokok",
                "Rapel Tunjangan Specialist",
                "Rapel Tunjangan Service",
                "Rapel Tunjangan Khusus",
                "Rapel Tunjangan Kemahalan",
                "Rapel Tunjangan Jabatan",
                "Rapel Tunjangan Transport",
                "Rapel Tunjangan Lain - lain",
                "Rapel Honorarium",
                "Rapel Tunjangan CAP",
                "Rapel Tunjangan MAP",
                "Rapel Bantuan Penugasan",
                "Rapel Tunjangan Kehadiran",
                "Rapel Overtime",
                "Rapel Overtime Meal",
                "Asuransi Inhealth",
                "Bantuan Biaya Melahirkan",
                "Bantuan Biaya Kacamata",
                "Pelunasan Motor Non Subsidi",
                "Pelunasan Pinjaman Pegawai",
                "Tunjangan DPLK",
                "Potongan DPLK",
                "Pesangon",
                "Penghargaan Masa Kerja",
                "Uang Pisah",
                "Tunjangan Pajak Irregular",
                "Tambahan Bantuan Penugasan",
                "Tambahan Gaji Pokok",
                "Tambahan Honorarium",
                "Tambahan Overtime",
                "Tambahan Overtime Meal",
                "Tambahan Tunjangan CAP",
                "Tambahan Tunjangan Jabatan",
                "Tambahan Tunjangan Kehadiran",
                "Tambahan Tunjangan Kemahalan",
                "Tambahan Tunjangan Khusus",
                "Tambahan Tunjangan Lain - lain",
                "Tambahan Tunjangan MAP",
                "Tambahan Tunjangan Service",
                "Tambahan Tunjangan Specialist",
                "Tambahan Tunjangan Transport",
                "Incentive",
                "Potongan Pajak Penalti",
                "Potongan Biaya Kacamata",
                "Potongan Biaya Melahirkan",
                "Tunjangan Cuti Besar",
                "Tunjangan Cuti Besar Formula",
                "Tunjangan Service Adj",
                "Tunjangan Jual Cuti Besar",
                "Bantuan Penugasan Adj",
                "Tunjangan Jabatan Adj",
                "Tunjangan Transport Adj",
                "Tunjangan Khusus Adj",
                "Potongan Pajak Irregular",
                "Rapel Tunjangan Asuransi CAP",
                "Potongan Asuransi Inhealth",
                "Incentive KI KMK",
                "Reward Contest AR",
                "Potongan Reward Contest AR",
                "Purna Jabatan",
                "Potongan Purna Jabatan",
                "Tunjangan DPLK Adj",
                "Uang Penggantian Hak Adj",
                "Uang Pisah Adj",
                "Pesangon Adj",
                "Penghargaan Masa Kerja Adj",
                "Kebijakan Perusahaan",
                "Additional Bonus",
                "Incentive Kartu Kredit",
                "Tunjangan Jual Cuti Besar Formula",
                "Adjustment Overtime",
                "Tambahan Tunjangan BPJS TK - JKK",
                "Tambahan Tunjangan BPJS TK - JKM",
                "Tambahan Tunjangan BPJS TK - JHT Comp",
                "Tambahan Potongan BPJS TK - JKK",
                "Tambahan Potongan BPJS TK - JKM",
                "Tambahan Potongan BPJS TK - JHT Comp",
                "Tambahan Potongan BPJS TK - JHT Emp",
                "Tambahan Tunjangan BPJS JP Comp",
                "Tambahan Potongan BPJS JP Comp",
                "Tambahan Potongan BPJS JP Emp",
                "Tambahan THR",
                "Tunjangan Komunikasi",
                "THR Non Formula",
                "Potongan Angsuran Sepeda Non Subsidi",
                "Insentif Pajak",
                "Reward WIRA Multiguna",
                "Incentive AMO",
                "Reward Repayment Restru",
                "Incentive Sales SHD Fleet",
                "Tunjangan Rangkap Jabatan",
                "Reward Booster",
                "Tunjangan Rumah",
                "Tambahan Tunjangan Rumah",
                "Tunjangan Koperasi - Pokok",
                "Tunjangan Operasional CAP Formula",
                "Tunjangan Koperasi - Wajib",
                "Potongan Koperasi - Wajib",
                "Tunjangan Operasional Listrik CAP Formula",
                "Tunjangan Operasional Listrik CAP",
                "Potongan Pinjaman Koperasi",
                "Uang Saku Perjalanan Dinas",
                "Reward Karyawan Pensiun",
                "Fasilitas Komunikasi",
                "Penghargaan Masa Kerja Karyawan",
                "Tunjangan Retain Talent",
                "Penghasilan Bruto Adj",
                "Potongan Penghasilan Bruto Adj",
                "Tunjangan Perbaikan dan Pemeliharaan",
                "Kompensasi PKWT"
        };
        for(String singleElement : elementListStr){
            ElementNameListDTO singleData = new ElementNameListDTO();
            singleData.setElementName(singleElement);
            elementList.add(singleData);
        }
        return elementList;
    }

}
