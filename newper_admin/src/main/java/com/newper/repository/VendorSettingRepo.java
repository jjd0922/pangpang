package com.newper.repository;

import com.newper.entity.Company;
import com.newper.entity.VendorSetting;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorSettingRepo extends JpaRepository <VendorSetting,Integer> {

    @EntityGraph(attributePaths = {"company"})
    VendorSetting findByVsIdx(int vsIdx);

    VendorSetting findByCompany(Company company);
}
