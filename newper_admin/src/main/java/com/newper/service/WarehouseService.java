package com.newper.service;

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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        String dataList = paramMap.getString("dataList");
        String[] dataArr = dataList.substring(0, (dataList.length() - 1)).split(",");
        warehouseMapper.changeAllWhState(dataArr, WhState.valueOf(paramMap.getString("state")));
    }

    /** 창고관리 > 로케이션 등록 */
    @Transactional
    public Integer saveLocation(Integer whIdx, ParamMap paramMap) {
        Location location = paramMap.mapParam(Location.class);
        location.setWarehouse(Warehouse.builder().whIdx(whIdx).build());
        location.setUser(User.builder().uIdx(paramMap.getInt("uIdx")).build());
        Location savedLoc = locationRepo.save(location);
        return savedLoc.getLocIdx();
    }

    /** 창고관리 > 로케이션 수정 */
    @Transactional
    public void updateLocation(Integer locIdx, ParamMap paramMap) {
        Location location = locationRepo.findById(locIdx).orElseThrow(() -> new MsgException("존재하지 않는 로케이션입니다."));
        Location locationParam = paramMap.mapParam(Location.class);
        location.updateLocation(locationParam);
        location.setUser(User.builder().uIdx(paramMap.getInt("uIdx")).build());
    }
}
