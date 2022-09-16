package com.newper.service;

import com.newper.dto.ParamMap;
import com.newper.entity.DeliveryNum;
import com.newper.entity.OrderGs;
import com.newper.exception.MsgException;
import com.newper.mapper.OrdersMapper;
import com.newper.repository.DeliveryNumRepo;
import com.newper.repository.OrdersGsRepo;
import com.newper.repository.OrdersRepo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeliverytService {

    private final OrdersRepo ordersRepo;
    private final DeliveryNumRepo deliveryNumRepo;
    private final OrdersGsRepo ordersGsRepo;
    private final OrdersMapper ordersMapper;



    /** 송장등록 엑셀 업로드*/
    @Transactional
    public String deliveryUpload(ParamMap paramMap, MultipartFile deliveryFile) {

        // 확장자명 확인
        String extension = FilenameUtils.getExtension(deliveryFile.getOriginalFilename());
        if (!extension.equals("xlsx") && !extension.equals("xls")) {
            throw new MsgException("엑셀파일만 업로드 가능합니다.");
        }

        // excel 내용 list로
        Workbook workbook = null;

        String result = "";
        try {
            if (extension.equals("xlsx")) {
                workbook = new XSSFWorkbook(deliveryFile.getInputStream());
            } else if (extension.equals("xls")) {
                workbook = new HSSFWorkbook(deliveryFile.getInputStream());
            }

            Sheet worksheet = workbook.getSheetAt(0);

            for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
                Map<String, Object> map = new HashMap<>();
                Row row = worksheet.getRow(i);

                String O_CODE = "";
                String GS_CODE = "";
                String G_BARCODE = "";
                String DN_COMPANY = "";
                String DN_NUM = "";

                for(int j=0; j<5; j++){
                    if(row.getCell(j)!=null){
                        switch(j){
                            case 0:
                                O_CODE=row.getCell(0).getStringCellValue();
                                break;
                            case 1:
                                GS_CODE=row.getCell(1).getStringCellValue();
                                break;
                            case 2:
                                G_BARCODE=row.getCell(2).getStringCellValue();
                                break;
                            case 3:
                                DN_COMPANY=row.getCell(3).getStringCellValue();
                                break;
                            case 4:
                                DN_NUM=String.valueOf((int)row.getCell(4).getNumericCellValue());
                                break;
                        }
                    }
                }

                // 셀이 빈칸일 경우 에러처리
                try {
                    if (O_CODE.equals("")) {
                        throw new MsgException("주문코드를 입력해주세요");
                    }
                    if (GS_CODE.equals("")) {
                        throw new MsgException("재고코드를 입력해주세요");
                    }

                    if (DN_COMPANY.equals("")) {
                        throw new MsgException("택배사를 입력해주세요");
                    }
                    if (DN_NUM.equals("")) {
                        throw new MsgException("송장번호를 입력해주세요");
                    }
                } catch (MsgException msgException) {
                    result += "<p>" + i + "번째 데이터에서 오류가 발생했습니다.</p>";
                    continue;
                }

                map.put("O_CODE", O_CODE);
                map.put("GS_CODE", GS_CODE);
                map.put("G_BARCODE", G_BARCODE);
                Long OG_IDX = ordersMapper.selectOrderGsDetailByOCodeAndGsCode(map);
                System.out.println(OG_IDX);
                if(OG_IDX!=null){
                    DeliveryNum deliveryNum = DeliveryNum.builder().build();
                    deliveryNum.setDnState("");
                    deliveryNum.setDnNum(DN_NUM);
                    deliveryNum.setDnCompany(DN_COMPANY);
                    deliveryNum.setCreatedDate(LocalDate.now());
                    deliveryNumRepo.save(deliveryNum);

                    OrderGs orderGs = ordersGsRepo.getReferenceById(OG_IDX);
                    orderGs.setDeliveryNum(deliveryNum);
                    ordersGsRepo.save(orderGs);
                }else{
                    result += "<p>" + i + "번째 데이터 해당하는 주문의 재고상품이 없습니다.</p>";
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
