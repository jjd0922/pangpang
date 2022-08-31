package com.newper.service;

import com.newper.dto.ParamMap;
import com.newper.exception.MsgException;
import com.newper.mapper.BomMapper;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BomService {

    private final BomMapper bomMapper;

    /** bom 등록*/
    @Transactional
    public void saveBom(ParamMap paramMap) {
        // 구성항목 null체크
        if (paramMap.getList("cpIdx").size() <= 0) {
            throw new MsgException("BOM구성항목을 입력해주세요.");
        }
        
        // 모상품이 이미 bom에서 모상품으로 등록되어 있는지 확인
        int cnt = bomMapper.countBom(paramMap.getMap());
        if (cnt > 0) {
            throw new MsgException("해당 상품의 BOM이 이미 존재합니다.");
        }
        
        // BOM 등록
        bomMapper.insertBom(paramMap.getMap());
    }

    /**bom 수정*/
    @Transactional
    public void updateBom(ParamMap paramMap) {
        if (paramMap.getList("cpIdx").size() <= 0) {
            throw new MsgException("BOM구성항목을 입력해주세요.");
        }
        bomMapper.deleteBom(paramMap.getMap());
        bomMapper.insertBom(paramMap.getMap());
    }

    /** 엑셀 업로드를 통한 bom 등록*/
    @Transactional
    public String uploadBomByExcel(ParamMap paramMap, MultipartFile excelFile) {
        List<Map<String, Integer>> bomList = new ArrayList<>();

        // 확장자명 확인
        String extension = FilenameUtils.getExtension(excelFile.getOriginalFilename());
        if (!extension.equals("xlsx") && !extension.equals("xls")) {
            throw new MsgException("엑셀파일만 업로드 가능합니다.");
        }

        // excel 내용 list로
        Workbook workbook = null;

        String result = "";
        try {
            if (extension.equals("xlsx")) {
                workbook = new XSSFWorkbook(excelFile.getInputStream());
            } else if (extension.equals("xls")) {
                workbook = new HSSFWorkbook(excelFile.getInputStream());
            }

            Sheet worksheet = workbook.getSheetAt(0);

            for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
                Map<String, Integer> map = new HashMap<>();
                Row row = worksheet.getRow(i);

                int mpIdx = (int) row.getCell(0).getNumericCellValue();
                int cpIdx = (int) row.getCell(1).getNumericCellValue();

                // 셀이 빈칸일 경우 에러처리
                try {
                    if (mpIdx <= 0) {
                        throw new MsgException("BOM 모상품을 입력해주세요");
                    } else if (cpIdx <= 0) {
                        throw new MsgException("BOM 구성상품을 입력해주세요");
                    }
                } catch (MsgException msgException) {
                    result += "<p>" + i + "번째 데이터에서 오류가 발생했습니다.</p>";
                    continue;
                }

                map.put("mpIdx", mpIdx);
                map.put("cpIdx", cpIdx);
                bomList.add(map);
            }

            bomMapper.insertBomByExcel(bomList);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
