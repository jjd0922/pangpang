package com.newper.service;

import com.newper.component.AdminBucket;
import com.newper.component.Common;
import com.newper.dto.ParamMap;
import com.newper.entity.DeliveryNum;
import com.newper.entity.Goods;
import com.newper.entity.OrderGs;
import com.newper.exception.MsgException;
import com.newper.mapper.OrdersMapper;
import com.newper.repository.DeliveryNumRepo;
import com.newper.repository.GoodsRepo;
import com.newper.repository.OrdersGsRepo;
import com.newper.repository.OrdersRepo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeliveryService {

    private final OrdersRepo ordersRepo;
    private final DeliveryNumRepo deliveryNumRepo;
    private final OrdersGsRepo ordersGsRepo;
    private final OrdersMapper ordersMapper;
    private final GoodsRepo goodsRepo;



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
                    deliveryNum.setDnJson(null);
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

    /**설치확인서 등록*/
    @Transactional
    public int saveInstall(ParamMap paramMap, MultipartFile DN_FILE){
        String[] idxs = paramMap.getString("idxs").split(",");
        int cnt =0;
        for(int i=0; i<idxs.length; i++){
            try {
                Long OG_IDX = Long.parseLong(idxs[i]);
                OrderGs orderGs = ordersGsRepo.getReferenceById(OG_IDX);
                String file = "";
                String fileName = "";

                DeliveryNum deliveryNum = paramMap.mapParam(DeliveryNum.class);
                if(DN_FILE.getSize()!=0){
                    file = Common.uploadFilePath(DN_FILE, "install/", AdminBucket.SECRET);
                    fileName= DN_FILE.getOriginalFilename();
                }

                JSONObject jsonObject = new JSONObject();
                JSONArray jsonArray = new JSONArray();

                //파일 추가시 파일 갯수만큼 반복문 적용
                for(int y=0; y<1; y++){
                    JSONArray jsonArray2 = new JSONArray();
                    jsonArray2.add(file);
                    jsonArray2.add(fileName);
                    jsonArray.add(jsonArray2);
                }
                jsonObject.put("files",jsonArray);
                jsonObject.put("memo",paramMap.getString("DN_MEMO"));

                deliveryNum.setDnState("");
                deliveryNum.setDnNum("");
                deliveryNum.setDnCompany(paramMap.getString("DN_COMPANY"));
                deliveryNum.setDnJson(jsonObject);
                deliveryNum.setDnSchedule(LocalDate.parse(paramMap.getString("DN_SCHEDULE"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                deliveryNum.setDnDate(LocalDate.parse(paramMap.getString("DN_DATE"),DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                deliveryNumRepo.save(deliveryNum);
                orderGs.setDeliveryNum(deliveryNum);
                ordersGsRepo.save(orderGs);
                cnt++;
            }catch (Exception e){
                continue;
            }

        }

        return cnt;
    }

    public String checkBarcode (String barcode){
        Goods goods = goodsRepo.findBygBarcode(barcode);
        if(goods == null){
            throw new MsgException("hello");
        }
        return "";
    }


}
