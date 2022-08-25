package com.newper.service;

import com.newper.constant.WhState;
import com.newper.dto.ParamMap;
import com.newper.entity.Company;
import com.newper.entity.Warehouse;
import com.newper.entity.common.Address;
import com.newper.exception.MsgException;
import com.newper.mapper.WarehouseMapper;
import com.newper.repository.WarehouseRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WarehouseService {

    private final WarehouseRepo warehouseRepo;
    private final WarehouseMapper warehouseMapper;

    /** 창고정보 등록 */
    @Transactional
    public Integer saveWarehouse(ParamMap paramMap) {
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
        List<Integer> whIdxList = paramMap.getList("dataList[]");
        warehouseMapper.changeAllWhState(whIdxList, WhState.valueOf(paramMap.getString("state")));
    }
}
