package com.newper.mapper;

import com.newper.constant.GiftState;
import com.newper.dto.ParamMap;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface GiftMapper {

    List<Map<String, Object>> selectGiftGroupDataTable(Map<String, Object> map);

    long countGiftGroupDataTable(Map<String, Object> map);

    List<Map<String, Object>> selectGiftDataTable(Map<String, Object> map);

    long countGiftDataTable(Map<String, Object> map);

    public void insertGift(@Param("giftgIdx") long giftgIdx,
                           @Param("giftgCnt") int giftgCnt,
                           @Param("giftState") GiftState giftState,
                           @Param("length") int length);

    public int countGiftByGiftgIdx(long giftgIdx);
}
