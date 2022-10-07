package com.newper.service;

import com.newper.component.SessionInfo;
import com.newper.constant.*;
import com.newper.dto.ParamMap;
import com.newper.entity.*;
import com.newper.exception.MsgException;
import com.newper.mapper.CalculateMapper;
import com.newper.repository.*;
import com.newper.repository.SabangRepo;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CalculateService {
    private final SessionInfo sessionInfo;
    private final CalculateGroupRepo calculateGroupRepo;
    private final CalculateAdjustRepo calculateAdjustRepo;
    private final OrdersGsRepo ordersGsRepo;
    private final VendorSettingRepo vendorSettingRepo;
    private final CompanyRepo companyRepo;
    private final CalculateMapper calculateMapper;

    private final OrdersRepo ordersRepo;
    private final SabangRepo sabangRepo;
    private final CalculateSalesRepo calculateSalesRepo;


    /** 정산 조정 완료 */
    @Transactional
    public void saveAdjust(ParamMap paramMap) {
        CalculateGroup calculateGroup = null;
        OrderGs orderGs = null;
        if (paramMap.get("ccgIdx") != null) {
            calculateGroup = calculateGroupRepo.findById(paramMap.getInt("ccgIdx")).get();

            // 정산완료인건 수정 불가
            if (calculateGroup.getCcgConfirmState().equals(CcgConfirmState.COMPLETE)) {
                throw new MsgException("해당 정산건은 이미 정산이 완료되었습니다.");
            }
        } else {
            orderGs = ordersGsRepo.findById(Long.parseLong(paramMap.getString("ogIdx"))).get();

            // 정산완료인건 수정 불가
//            if (orderGs.getOgCalConfirmState().equals(CcgConfirmState.COMPLETE)) {
//                throw new MsgException("해당 정산건은 이미 정산이 완료되었습니다.");
//            }
        }

        int ccgAdjustCost = 0;

        List<Long> ccaIdx = paramMap.getListLong("ccaIdx");
        List<String> ccaContent = paramMap.getList("ccaContent");
        List<String> ccaReason = paramMap.getList("ccaReason");
        List<Long> ccaCost = paramMap.getListLong("ccaCost");
        for (int i = 0; i < ccaIdx.size(); i++) {
            CalculateAdjust calculateAdjust = CalculateAdjust
                    .builder()
                    .ccaIdx(ccaIdx.get(i).intValue())
                    .ccaContent(ccaContent.get(i))
                    .ccaReason(ccaReason.get(i))
                    .ccaCost(ccaCost.get(i).intValue())
                    .build();

            if (paramMap.get("ccgIdx") != null) {
                calculateAdjust.setCalculateGroup(calculateGroup);
            } else {
                calculateAdjust.setOrderGs(orderGs);
            }

            ccgAdjustCost += ccaCost.get(i);

            calculateAdjustRepo.save(calculateAdjust);
        }

        if (paramMap.get("ccgIdx") != null) {
            calculateGroup.setCcgAdjustCost(ccgAdjustCost);
            calculateGroup.setCcgAdjust(CcgAdjust.ADJUST);
            calculateGroupRepo.save(calculateGroup);
        } else {
//            orderGs.setOgCalAdjustCost(ccgAdjustCost);
//            orderGs.setOgCalAdjust(CcgAdjust.ADJUST);
        }
    }


    /** 정산 조정 삭제 */
    @Transactional
    public void removeAdjust(ParamMap paramMap) {
        int adjustCost = 0;
        CalculateGroup calculateGroup = null;
        OrderGs orderGs = null;
        if (paramMap.get("ccgIdx") != null) {
            calculateGroup = calculateGroupRepo.findById(paramMap.getInt("ccgIdx")).get();

            // 정산완료인건 수정 불가
            if (calculateGroup.getCcgConfirmState().equals(CcgConfirmState.COMPLETE)) {
                throw new MsgException("해당 정산건은 이미 정산이 완료되었습니다.");
            }

            adjustCost = calculateGroup.getCcgAdjustCost();

            CalculateAdjust calculateAdjust = calculateAdjustRepo.findById(paramMap.getInt("ccaIdx")).get();
            adjustCost -= calculateAdjust.getCcaCost();

            calculateGroup.setCcgAdjustCost(adjustCost);

            if (adjustCost == 0) {
                calculateGroup.setCcgAdjust(CcgAdjust.NORMAL);
            }

            calculateAdjustRepo.deleteById(paramMap.getInt("ccaIdx"));
            calculateGroupRepo.save(calculateGroup);
        } else {
            orderGs = ordersGsRepo.findById(Long.parseLong(paramMap.getString("ogIdx"))).get();

            // 정산완료인건 수정 불가
//            if (orderGs.getOgCalConfirmState().equals(CcgConfirmState.COMPLETE)) {
//                throw new MsgException("해당 정산건은 이미 정산이 완료되었습니다.");
//            }
//            adjustCost = orderGs.getOgCalAdjustCost();

            CalculateAdjust calculateAdjust = calculateAdjustRepo.findById(paramMap.getInt("ccaIdx")).get();
            adjustCost -= calculateAdjust.getCcaCost();

//            orderGs.setOgCalAdjustCost(adjustCost);
//
//            if (adjustCost == 0) {
//                orderGs.setOgCalAdjust(CcgAdjust.NORMAL);
//            }

            calculateAdjustRepo.deleteById(paramMap.getInt("ccaIdx"));
            ordersGsRepo.save(orderGs);
        }

    }

    /** 정산상태 수정 */
    @Transactional
    public void updateConfirmState(ParamMap paramMap) {
        CalculateGroup calculateGroup = calculateGroupRepo.findById(paramMap.getInt("ccgIdx")).get();
        CcgConfirmState ccgConfirmState_ori = calculateGroup.getCcgConfirmState();
        CcgConfirmState ccgConfirmState_new = CcgConfirmState.valueOf(paramMap.getString("ccgConfirmState"));

        // 해당 정산건 마감 완료시 제한
        if (ccgConfirmState_ori.equals(CcgCloseState.COMPLETE)) {
            throw new MsgException("해당정산건은 마감되어 상태변경이 불가능합니다.");
        }

        // 정산확정인 상태에서 정산확정 처리가 들어올시
        if (ccgConfirmState_ori.equals(CcgConfirmState.COMPLETE) && ccgConfirmState_new.equals(CcgConfirmState.COMPLETE)) {
            throw new MsgException("이미 정산 확정상태입니다.");
        }

        // 정산취소인 상태에서 정산취소 처리가 들어올시
        if (ccgConfirmState_ori.equals(CcgConfirmState.CANCEL) && ccgConfirmState_new.equals(CcgConfirmState.CANCEL)) {
            throw new MsgException("이미 정산 확정취소상태입니다.");
        }

        //정산대기 상태에서 정산취소 처리가 들어올시
        if (ccgConfirmState_ori.equals(CcgConfirmState.WAIT) && ccgConfirmState_new.equals(CcgConfirmState.CANCEL)) {
            throw new MsgException("정산대기인 정산건은 취소 할 수 없습니다.");
        }

        calculateGroup.setCcgConfirmState(ccgConfirmState_new);
        calculateGroup.setCcgConfirmDate(LocalDate.now());
        calculateGroup.setCcgConfirmTime(LocalTime.now());
        calculateGroup.setCcgConfirmBy(sessionInfo.getId());
        calculateGroup.setCcgMemo(paramMap.getString("ccgMemo"));
        calculateGroupRepo.save(calculateGroup);
    }

    /** 정산상태 여러건 수정 */
    @Transactional
    public void updateConfirmStateArr(ParamMap paramMap) {
        String[] ccgIdx = paramMap.getString("ccgIdx").split(",");
        for (int i = 0; i < ccgIdx.length; i++) {
            CalculateGroup calculateGroup = calculateGroupRepo.findById(Integer.parseInt(ccgIdx[i])).get();
            CcgConfirmState ccgConfirmState_ori = calculateGroup.getCcgConfirmState();
            CcgConfirmState ccgConfirmState_new = CcgConfirmState.valueOf(paramMap.getString("ccgConfirmState"));

            // 해당 정산건 마감 완료시 제한
            if (ccgConfirmState_ori.equals(CcgCloseState.COMPLETE)) {
                throw new MsgException("해당정산건은 마감되어 상태변경이 불가능합니다.");
            }

            // 정산확정인 상태에서 정산확정 처리가 들어올시
            if (ccgConfirmState_ori.equals(CcgConfirmState.COMPLETE) && ccgConfirmState_new.equals(CcgConfirmState.COMPLETE)) {
                throw new MsgException("이미 정산 확정상태입니다.");
            }

            // 정산취소인 상태에서 정산취소 처리가 들어올시
            if (ccgConfirmState_ori.equals(CcgConfirmState.CANCEL) && ccgConfirmState_new.equals(CcgConfirmState.CANCEL)) {
                throw new MsgException("이미 정산 확정취소상태입니다.");
            }

            //정산대기 상태에서 정산취소 처리가 들어올시
            if (ccgConfirmState_ori.equals(CcgConfirmState.WAIT) && ccgConfirmState_new.equals(CcgConfirmState.CANCEL)) {
                throw new MsgException("정산대기인 정산건은 취소 할 수 없습니다.");
            }

            calculateGroup.setCcgConfirmState(ccgConfirmState_new);
            calculateGroup.setCcgConfirmDate(LocalDate.now());
            calculateGroup.setCcgConfirmTime(LocalTime.now());
            calculateGroup.setCcgConfirmBy(sessionInfo.getId());
            calculateGroup.setCcgMemo(paramMap.getString("ccgMemo"));
            calculateGroupRepo.save(calculateGroup);
        }
    }

    /** 마감상태 수정 */
    @Transactional
    public void updateCloseState(ParamMap paramMap) {
        CalculateGroup calculateGroup = calculateGroupRepo.findById(paramMap.getInt("ccgIdx")).get();
        CcgCloseState ccgCloseState_ori = calculateGroup.getCcgCloseState();
        CcgCloseState ccgCloseState_new = CcgCloseState.valueOf(paramMap.getString("ccgCloseState"));

        // 정상 미확정건 마감 처리 불가
        if (!calculateGroup.getCcgConfirmState().equals(CcgConfirmState.COMPLETE)) {
            throw new MsgException("정산미확정정 건으로 마감 처리 할 수 없습니다.");
        }

        // 해당 정산건 마감완료인데 마감완료요청시 불가
        if (ccgCloseState_ori.equals(CcgCloseState.COMPLETE) && ccgCloseState_new.equals(CcgCloseState.COMPLETE)) {
            throw new MsgException("이미 마감 완료된 정산건입니다.");
        }

        // 해당 정산건 마감취소인데 마감취소요청시 불가
        if (ccgCloseState_ori.equals(CcgCloseState.CANCEL) && ccgCloseState_new.equals(CcgCloseState.CANCEL)) {
            throw new MsgException("이미 마감 완료된 정산건입니다.");
        }

        // 해당 정산건 마감취대기인데인데 마감취소요청시 불가
        if (ccgCloseState_ori.equals(CcgCloseState.WAIT) && ccgCloseState_new.equals(CcgCloseState.CANCEL)) {
            throw new MsgException("마감 완료된 정산건만 마감 취소 할 수 있습니다.");
        }

        calculateGroup.setCcgCloseTime(LocalTime.now());
        calculateGroup.setCcgCloseDate(LocalDate.now());
        calculateGroup.setCcgCloseBy(sessionInfo.getId());
        calculateGroup.setCcgCloseState(ccgCloseState_new);
        calculateGroup.setCcgCloseMemo(paramMap.getString("ccgCloseMemo"));
        calculateGroupRepo.save(calculateGroup);
    }

    /** 마감상태 여러건 수정 */
    @Transactional
    public void updateCloseStateArr(ParamMap paramMap) {
        String[] ccgIdx = paramMap.getString("ccgIdx").split(",");

        for (int i = 0; i < ccgIdx.length; i++) {
            CalculateGroup calculateGroup = calculateGroupRepo.findById(Integer.parseInt(ccgIdx[i])).get();
            CcgCloseState ccgCloseState_ori = calculateGroup.getCcgCloseState();
            CcgCloseState ccgCloseState_new = CcgCloseState.valueOf(paramMap.getString("ccgCloseState"));

            // 정상 미확정건 마감 처리 불가
            if (!ccgCloseState_ori.equals(CcgConfirmState.COMPLETE)) {
                throw new MsgException("정산미확정정 건으로 마감 처리 할 수 없습니다.");
            }

            // 해당 정산건 마감완료인데 마감완료요청시 불가
            if (ccgCloseState_ori.equals(CcgCloseState.COMPLETE) && ccgCloseState_new.equals(CcgCloseState.COMPLETE)) {
                throw new MsgException("이미 마감 완료된 정산건입니다.");
            }

            // 해당 정산건 마감취소인데 마감취소요청시 불가
            if (ccgCloseState_ori.equals(CcgCloseState.CANCEL) && ccgCloseState_new.equals(CcgCloseState.CANCEL)) {
                throw new MsgException("이미 마감 완료된 정산건입니다.");
            }

            // 해당 정산건 마감취대기인데인데 마감취소요청시 불가
            if (ccgCloseState_ori.equals(CcgCloseState.WAIT) && ccgCloseState_new.equals(CcgCloseState.CANCEL)) {
                throw new MsgException("마감 완료된 정산건만 마감 취소 할 수 있습니다.");
            }

            calculateGroup.setCcgCloseTime(LocalTime.now());
            calculateGroup.setCcgCloseDate(LocalDate.now());
            calculateGroup.setCcgCloseBy(sessionInfo.getId());
            calculateGroup.setCcgCloseState(ccgCloseState_new);
            calculateGroup.setCcgCloseMemo(paramMap.getString("ccgCloseMemo"));
            calculateGroupRepo.save(calculateGroup);
        }
    }

    /** 매출 정산 처리 완료 */
    @Transactional
    public void updateConfirmStateSales(ParamMap paramMap) {
        String[] ogIdx = paramMap.getString("ogIdx").split(",");
        for (int i = 0; i < ogIdx.length; i++) {
//            OrderGs orderGs = ordersGsRepo.findById(Long.parseLong(ogIdx[i])).get();
//            OgCalConfirmState ogConfirmState_ori = orderGs.getOgCalConfirmState();
//            OgCalConfirmState ogConfirmState_new = OgCalConfirmState.valueOf(paramMap.getString("ogCalConfirmState"));
//
//            // 해당 정산건 마감 완료시 제한
//            if (ogConfirmState_ori.equals(CcgCloseState.COMPLETE)) {
//                throw new MsgException("해당정산건은 마감되어 상태변경이 불가능합니다.");
//            }
//
//            // 정산확정인 상태에서 정산확정 처리가 들어올시
//            if (ogConfirmState_ori.equals(CcgConfirmState.COMPLETE) && ogConfirmState_new.equals(CcgConfirmState.COMPLETE)) {
//                throw new MsgException("이미 정산 확정상태입니다.");
//            }
//
//            // 정산취소인 상태에서 정산취소 처리가 들어올시
//            if (ogConfirmState_ori.equals(CcgConfirmState.CANCEL) && ogConfirmState_new.equals(CcgConfirmState.CANCEL)) {
//                throw new MsgException("이미 정산 확정취소상태입니다.");
//            }
//
//            //정산대기 상태에서 정산취소 처리가 들어올시
//            if (ogConfirmState_ori.equals(CcgConfirmState.WAIT) && ogConfirmState_new.equals(CcgConfirmState.CANCEL)) {
//                throw new MsgException("정산대기인 정산건은 취소 할 수 없습니다.");
//            }
//
//            orderGs.setOgCalConfirmState(ogConfirmState_new);
//            orderGs.setOgCalConfirmMemo(paramMap.getString("ogCalConfirmMemo"));
//            orderGs.setOgCalConfirmBy(sessionInfo.getId());
//            orderGs.setOgCalConfirmDate(LocalDate.now());
//            ordersGsRepo.save(orderGs);
        }
    }

    /** 매출정산 마감 처리 */
    @Transactional
    public void updateCloseStateSales(ParamMap paramMap) {
//        String[] ogIdx = paramMap.getString("ogIdx").split(",");
//        for (int i = 0; i < ogIdx.length; i++) {
//            OrderGs orderGs = ordersGsRepo.findById(Long.parseLong(ogIdx[i])).get();
//            OgCalCloseState ogCalCloseState_ori = orderGs.getOgCalCloseState();
//            OgCalCloseState ogCalCloseState_new = OgCalCloseState.valueOf(paramMap.getString("ogCalCloseState"));
//
//            // 정상 미확정건 마감 처리 불가
//            if (!ogCalCloseState_ori.equals(OgCalCloseState.COMPLETE)) {
//                throw new MsgException("정산미확정정 건으로 마감 처리 할 수 없습니다.");
//            }
//
//            // 해당 정산건 마감완료인데 마감완료요청시 불가
//            if (ogCalCloseState_ori.equals(OgCalCloseState.COMPLETE) && ogCalCloseState_new.equals(OgCalCloseState.COMPLETE)) {
//                throw new MsgException("이미 마감 완료된 정산건입니다.");
//            }
//
//            // 해당 정산건 마감취소인데 마감취소요청시 불가
//            if (ogCalCloseState_ori.equals(OgCalCloseState.CANCEL) && ogCalCloseState_new.equals(OgCalCloseState.CANCEL)) {
//                throw new MsgException("이미 마감 완료된 정산건입니다.");
//            }
//
//            // 해당 정산건 마감취대기인데인데 마감취소요청시 불가
//            if (ogCalCloseState_ori.equals(OgCalCloseState.WAIT) && ogCalCloseState_new.equals(OgCalCloseState.CANCEL)) {
//                throw new MsgException("마감 완료된 정산건만 마감 취소 할 수 있습니다.");
//            }
//
//            orderGs.setOgCalCloseState(ogCalCloseState_new);
//            orderGs.setOgCalCloseBy(sessionInfo.getId());
//            orderGs.setOgCalCloseDate(LocalDate.now());
//            orderGs.setOgCalCloseMemo(paramMap.getString("ogCalCloseMemo"));
//            ordersGsRepo.save(orderGs);
//        }
    }

    @Transactional
    public Integer saveVendor(ParamMap paramMap) {
        VendorSetting vendorSetting = paramMap.mapParam(VendorSetting.class);
        Company company = Company
                .builder()
                .comIdx(Integer.parseInt(paramMap.get("COM_IDX").toString()))
                .build();
        vendorSetting.setCompany(company);

        try {
            int check = Integer.parseInt(paramMap.get("COM_IDX").toString());
        } catch (NumberFormatException nfe) {
            throw new MsgException("거래처(판매처)를 선택해 주세요");
        }

        String vsType = paramMap.getString("vsType");
        if (vsType == null || vsType.equals("")) {
            throw new MsgException("타입을 선택해주세요");
        }

//        숫자 = try catech
//        문자열 = if null || ""


        vendorSettingRepo.save(vendorSetting);
        return vendorSetting.getVsIdx();
    }
    /**벤더정산 팝업삭제*/
    @Transactional
    public void deleteVendor(ParamMap paramMap){
        List<Long> vsIdx = paramMap.getListLong("vsIdx[]");
        for (int i = 0; i < vsIdx.size(); i++) {
            vendorSettingRepo.deleteById(vsIdx.get(i).intValue());
        }
    }

    /**
     * 벤더정산 업데이트
     */
    @Transactional
    public Integer updateVendor(ParamMap paramMap,Integer vsIdx){
        VendorSetting cal = vendorSettingRepo.findById(vsIdx).get();
        VendorSetting vendorSetting = paramMap.mapParam(VendorSetting.class);
        Company company = paramMap.mapParam(Company.class);

        /*  cal.setVsCal(vendorSetting.getVsCal());*/
        cal.setCompany(company);
        cal.setVsPrice(vendorSetting.getVsPrice());
        cal.setVsDeliveryCost(vendorSetting.getVsDeliveryCost());
        cal.setVsOrder(vendorSetting.getVsOrder());
        cal.setVsFee(vendorSetting.getVsFee());
        cal.setVsRealPrice(vendorSetting.getVsRealPrice());
        cal.setVsProduct(vendorSetting.getVsProduct());
        cal.setVsMemo(vendorSetting.getVsMemo());

        vendorSettingRepo.save(cal);
        return cal.getVsIdx();
    }

    /** 벤더사 정산 엑셀 업로드 **/
    @Transactional
    public void uploadVendor(ParamMap paramMap, MultipartFile excelFile) throws IOException {
        List<Map<String, Object>> column = calculateMapper.selectExcelColumn();
        Company company = companyRepo.findById(paramMap.getInt("comIdx")).get();
        VendorSetting vendorSetting = vendorSettingRepo.findByCompany(company);
        if (vendorSetting == null) {
            throw new MsgException("정산 설정이 등록되어 있지 않은 거래처입니다.");
        }

        String fileName = excelFile.getOriginalFilename();
        if (fileName == null || !fileName.contains(".")) {
            throw new MsgException("엑셀 형식의 파일을 업로드 해주세요.");
        }
        String ver = fileName.substring(fileName.lastIndexOf(".") + 1);
        // excel이 아닌 경우
        if (!(ver.equals("xlsx") || ver.equals("xls"))) {
            throw new MsgException("xlsx 또는 xls 확장자 파일을 업로드 해주세요.");
        }


//        Common.uploadFilePath(excelFile, "vendor/"+company.getComName()+"/", AdminBucket.SECRET);

        Workbook wb = null;
        InputStream fileInputStream = new ByteArrayInputStream(excelFile.getBytes());
        if (ver.equals("xlsx")) {
            wb = new XSSFWorkbook(fileInputStream);
        } else {// ver.equals("xls")
            wb = new HSSFWorkbook(fileInputStream);
        }

        Sheet sheet = wb.getSheetAt(0);
        int rows = sheet.getPhysicalNumberOfRows();// 행 갯수
        Row firstRow = sheet.getRow(0);

        // 기준열
        String vsOrder = vendorSetting.getVsOrder(); // 주문번호
        String vsProduct = vendorSetting.getVsProduct(); // 상품코드
        String vsPrice = vendorSetting.getVsPrice(); //판매금액
        String vsDeliveryCost = vendorSetting.getVsDeliveryCost(); // 배송비
        String vsFee = vendorSetting.getVsFee(); // 수수료
        String vsRealPrice = vendorSetting.getVsRealPrice(); // 정산금액

        // 열 알파벳 번호로 치환
        Map<String, Integer> columnMap = new HashMap<>();
        for (int i = 0; i < column.size(); i++) {
            if (vsOrder.equals(column.get(i).get("EC_COLUMN"))) {
                columnMap.put(vsOrder, Integer.parseInt(column.get(i).get("EC_IDX").toString()));
            }
            if (vsProduct.equals(column.get(i).get("EC_COLUMN"))) {
                columnMap.put(vsProduct, Integer.parseInt(column.get(i).get("EC_IDX").toString()));
            }
            if (vsPrice.equals(column.get(i).get("EC_COLUMN"))) {
                columnMap.put(vsPrice, Integer.parseInt(column.get(i).get("EC_IDX").toString()));
            }
            if (vsDeliveryCost.equals(column.get(i).get("EC_COLUMN"))) {
                columnMap.put(vsDeliveryCost, Integer.parseInt(column.get(i).get("EC_IDX").toString()));
            }
            if (vsFee.equals(column.get(i).get("EC_COLUMN"))) {
                columnMap.put(vsFee, Integer.parseInt(column.get(i).get("EC_IDX").toString()));
            }
            if (vsRealPrice.equals(column.get(i).get("EC_COLUMN"))) {
                columnMap.put(vsRealPrice, Integer.parseInt(column.get(i).get("EC_IDX").toString()));
            }
        }

        for (int i = 0; i < rows; i++) {
            Row row = sheet.getRow(i);
            String oCode = row.getCell(columnMap.get(vsOrder)).toString();
            Orders orders = ordersRepo.findByoCode(oCode);

            if (orders == null) {
                throw new MsgException("없는 주문번호 입니다.");
            }

            String saMallProductId = row.getCell(columnMap.get(vsProduct)).toString();
            Sabang sabang = sabangRepo.findBySaMallProductId(saMallProductId);

            if (sabang == null) {
                throw new MsgException("없는 상품 주분 번호 입니다.");
            }

            Cell price = row.getCell(Integer.parseInt(columnMap.get(vsPrice).toString()));
            Cell delivery = row.getCell(Integer.parseInt(columnMap.get(vsDeliveryCost).toString()));
            Cell fee = row.getCell(Integer.parseInt(columnMap.get(vsFee).toString()));
            Cell finalPrice = row.getCell(Integer.parseInt(columnMap.get(vsRealPrice).toString()));

            OrderGs orderGs = sabang.getOrderGs();
            CalculateSales calculateSales = calculateSalesRepo.findByOrderGs(orderGs);

            calculateSales.setCcsPrice((int) price.getNumericCellValue());
            calculateSales.setCcsDelivery((int) delivery.getNumericCellValue());
            calculateSales.setCcsFee((int) fee.getNumericCellValue());
            calculateSales.setCcsFinalCost((int) finalPrice.getNumericCellValue());

            calculateSalesRepo.save(calculateSales);
        }
    }
}
