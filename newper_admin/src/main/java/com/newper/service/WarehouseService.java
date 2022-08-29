package com.newper.service;

import com.newper.constant.LocForm;
import com.newper.constant.LocType;
import com.newper.constant.WhState;
import com.newper.dto.ParamMap;
import com.newper.entity.Company;
import com.newper.entity.Location;
import com.newper.entity.User;
import com.newper.entity.Warehouse;
import com.newper.entity.common.Address;
import com.newper.exception.MsgException;
import com.newper.mapper.WarehouseMapper;
import com.newper.repository.LocationRepo;
import com.newper.repository.WarehouseRepo;
import com.newper.util.ExcelDownload;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WarehouseService {

    private final WarehouseRepo warehouseRepo;
    private final WarehouseMapper warehouseMapper;
    private final LocationRepo locationRepo;

    /** 창고정보 등록 */
    @Transactional
    public Integer saveWarehouse(ParamMap paramMap) {
        // 거래처 입력 체크
        if (!StringUtils.hasText(paramMap.getString("comIdx"))) {
            throw new MsgException("거래처를 입력해주세요");
        }

        Warehouse warehouse = paramMap.mapParam(Warehouse.class);
        Address address = paramMap.mapParam(Address.class);
        Company company = Company.builder().comIdx(paramMap.getInt("comIdx")).build();

        warehouse.setAddress(address);
        warehouse.setCompany(company);
        Warehouse savedWh = warehouseRepo.save(warehouse);
        return savedWh.getWhIdx();
    }

    /** 창고정보 수정 */
    @Transactional
    public void updateWarehouse(Integer whIdx, ParamMap paramMap) {
        // 거래처 입력 체크
        if (!StringUtils.hasText(paramMap.getString("comIdx"))) {
            throw new MsgException("거래처를 입력해주세요");
        }

        Warehouse warehouse = warehouseRepo.findById(whIdx).orElseThrow(() -> new MsgException("존재하지 않는 창고입니다."));
        Warehouse newWh = paramMap.mapParam(Warehouse.class);
        warehouse.setWhName(newWh.getWhName());
        warehouse.setWhState(newWh.getWhState());
        warehouse.setCompany(Company.builder().comIdx(paramMap.getInt("comIdx")).build());
        warehouse.setAddress(paramMap.mapParam(Address.class));
    }

    /** 창고 상태 일괄변경 */
    @Transactional
    public void changeWhState(ParamMap paramMap) {
        String dataList = paramMap.getString("dataList");
        String[] dataArr = dataList.substring(0, (dataList.length() - 1)).split(",");
        warehouseMapper.changeAllWhState(dataArr, WhState.valueOf(paramMap.getString("state")));
    }

    /** 창고관리 > 로케이션 등록 */
    @Transactional
    public Integer saveLocation(Integer whIdx, ParamMap paramMap) {
        // 담당자 입력 체크
        if (!StringUtils.hasText(paramMap.getString("uIdx"))) {
            throw new MsgException("로케이션 담당자를 입력해주세요");
        }
        Location location = paramMap.mapParam(Location.class);
        location.setWarehouse(Warehouse.builder().whIdx(whIdx).build());
        location.setUser(User.builder().uIdx(paramMap.getInt("uIdx")).build());
        Location savedLoc = locationRepo.save(location);
        return savedLoc.getLocIdx();
    }

    /** 창고관리 > 로케이션 수정 */
    @Transactional
    public void updateLocation(Integer locIdx, ParamMap paramMap) {
        // 담당자 입력 체크
        if (!StringUtils.hasText(paramMap.getString("uIdx"))) {
            throw new MsgException("로케이션 담당자를 입력해주세요");
        }
        Location location = locationRepo.findById(locIdx).orElseThrow(() -> new MsgException("존재하지 않는 로케이션입니다."));
        Location locationParam = paramMap.mapParam(Location.class);
        location.updateLocation(locationParam);
        location.setUser(User.builder().uIdx(paramMap.getInt("uIdx")).build());
    }

    /**창고관리 > 로케이션구분 일괄변경*/
    @Transactional
    public void changeLocType(ParamMap paramMap) {
        String dataList = paramMap.getString("dataList");
        String[] dataArr = dataList.substring(0, (dataList.length() - 1)).split(",");
        warehouseMapper.changeAllLocType(dataArr, LocType.valueOf(paramMap.getString("locType")));
    }

    /**로케이션 엑셀업로드*/
    @Transactional
    public String uploadLocationByExcel(ParamMap paramMap, MultipartFile excelFile) {
        List<Location> locationList = new ArrayList<>();

        if (!StringUtils.hasText(paramMap.getString("whIdx"))) {
            throw new MsgException("존재하지 않는 창고입니다.");
        }
        Warehouse warehouse = Warehouse.builder().whIdx(paramMap.getInt("whIdx")).build();

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
                Row row = worksheet.getRow(i);

                // 하나의 행(row)에서 받아온 내용들로 location set
                LocType locType = null;
                LocForm locForm = null;
                if (StringUtils.hasText(row.getCell(0).getStringCellValue())) {
                    locType = LocType.valueOf(row.getCell(0).getStringCellValue());
                }
                if (StringUtils.hasText(row.getCell(1).getStringCellValue())) {
                    locForm = LocForm.valueOf(row.getCell(1).getStringCellValue());
                }
                String locZone = row.getCell(2).getStringCellValue();
                String locRow = ExcelDownload.getStringCellValueOfExcelRow(row, 3); // 숫자형태면 String으로 변환
                String locColumn = ExcelDownload.getStringCellValueOfExcelRow(row, 4);
                String locCode = locZone + locRow + locColumn;

                Location location = Location.builder()
                        .warehouse(warehouse)
                        .locType(locType)
                        .locForm(locForm)
                        .locZone(locZone)
                        .locRow(locRow)
                        .locColumn(locColumn)
                        .locCode(locCode)
                        .build();

                try {
                    location.preSave();
                } catch (MsgException msgException) {
                    result += "<p>" + i + "번째 데이터에서 오류가 발생했습니다.</p>";
                    continue;
                }

                locationList.add(location);
            }

            warehouseMapper.insertLocationByExcel(locationList);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

}
