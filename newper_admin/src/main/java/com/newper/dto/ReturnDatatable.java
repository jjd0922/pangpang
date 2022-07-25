package com.newper.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Getter
/** dataTable return용 class*/
public class ReturnDatatable {

    private long recordsFiltered;
    private long recordsTotal;
    private List<?> data=new ArrayList<>();

    private LinkedHashMap<String, Object> map = new LinkedHashMap<>();

    public void setData(List data) {
        this.data.clear();
        if(data!=null){
            this.data.addAll(data);
        }
    }

    public void setRecordsTotal(long recordsTotal) {
        this.recordsTotal = recordsTotal;
        this.recordsFiltered = recordsTotal;
    }
    public void setRecordsTotal() {
        this.recordsTotal = data.size();
        this.recordsFiltered = recordsTotal;
    }

    public void put(String key, Object value) {
        map.put(key, value);
    }

    /**footer에 사용되는 map*/
    public void putCountMap(Map<String,Object> countMap){
        map.put("countMap", countMap);
        if(countMap.containsKey("CNT")){
            setRecordsTotal(Long.parseLong(String.valueOf(countMap.get("CNT"))));
        }
    }
}
