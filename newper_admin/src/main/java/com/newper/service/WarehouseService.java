package com.newper.service;

import com.newper.dto.ParamMap;
import com.newper.entity.Company;
import com.newper.entity.Warehouse;
import com.newper.entity.common.Address;
import com.newper.exception.MsgException;
import com.newper.repository.WarehouseRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WarehouseService {

    private final WarehouseRepo warehouseRepo;

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

    @Transactional
    public void updateWarehouse(Integer whIdx, ParamMap paramMap) {
        Warehouse warehouse = warehouseRepo.findById(whIdx).orElseThrow(() -> new MsgException("존재하지 않는 창고입니다."));
        Warehouse newWh = paramMap.mapParam(Warehouse.class);
        warehouse.setWhName(newWh.getWhName());
        warehouse.setWhState(newWh.getWhState());
        warehouse.setCompany(Company.builder().comIdx(paramMap.getInt("comIdx")).build());
        warehouse.setAddress(paramMap.mapParam(Address.class));
    }
}
