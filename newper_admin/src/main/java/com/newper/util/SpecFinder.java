package com.newper.util;

import com.newper.entity.Spec;
import com.newper.entity.SpecList;
import com.newper.mapper.SpecMapper;
import com.newper.repository.SpecListRepo;
import com.newper.repository.SpecRepo;
import lombok.RequiredArgsConstructor;

import java.util.*;

/** spec 그룹 찾아주는 class. dao 객체를 전달 받아서 사용*/
@RequiredArgsConstructor
public class SpecFinder {

    private final SpecMapper specMapper;
    private final SpecListRepo specListRepo;
    private final SpecRepo specRepo;

    /**key : specName, value : {key : specValue }*/
    private Map<String, Map<String, SpecList>> specNameMap = new HashMap<>();

    /** specl_name, specl_value로 specl_idx 가져오기*/
    public SpecList findSpecList(String speclName, String speclValue){
        Map<String, SpecList> specValueMap = null;
        if(!specNameMap.containsKey(speclName)){
            specValueMap = specMapper.selectSpecListMap(speclName);
            specNameMap.put(speclName, specValueMap);
        }else{
            specValueMap = specNameMap.get(speclName);
        }

        if(specValueMap == null){
            specValueMap = new HashMap<>();
            specNameMap.put(speclName, specValueMap);
        }

        SpecList specList = specValueMap.get(speclValue);
        //해당 스펙 처음 사용하는 경우 db에 insert
        if(specList == null){
            specList = SpecList.builder()
                    .speclName(speclName)
                    .speclValue(speclValue)
                    .build();
            specListRepo.save(specList);
            specValueMap.put(speclValue, specList);
        }

        return specList;
    }

    /** 스펙그룹 찾기*/
    public Spec findSpec(List<String> specNameList, List<String> specValueList){
        List<Integer> specConfirmList = new ArrayList<>();
        String specLookup = "";
        for (int i = 0; i < specNameList.size(); i++) {
            SpecList specList = findSpecList(specNameList.get(i), specValueList.get(i));

            specConfirmList.add(specList.getSpeclIdx());
            if (i+1 < specNameList.size()) {
                specLookup += specNameList.get(i) +":"+specValueList.get(i)+"/";
            }else{
                //마지막 스펙인 경우
                specLookup += specNameList.get(i) +":"+specValueList.get(i);
            }
        }
        //specConfirm 정렬
        Collections.sort(specConfirmList);
        String specConfirm = "";
        for (int i = 0; i < specConfirmList.size(); i++) {
            if (i+1 < specConfirmList.size()) {
                specConfirm += specConfirmList.get(i)+"_";
            }else{
            //마지막 스펙인 경우
                specConfirm += specConfirmList.get(i);
            }
        }

        Spec spec = specRepo.findSpecBySpecConfirm(specConfirm);
        if (spec == null) {
            spec = Spec.builder()
                    .specConfirm(specConfirm)
                    .specLookup(specLookup)
                    .build();
            specRepo.save(spec);
        }
        return spec;
    }
}
