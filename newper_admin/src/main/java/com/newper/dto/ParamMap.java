package com.newper.dto;

import com.newper.converter.ConverterLocalDate;
import com.newper.exception.MsgException;
import net.bytebuddy.asm.Advice;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.ValidationException;
import org.modelmapper.config.Configuration;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ParamMap {
    Map<String,Object> map=new HashMap<String,Object>();
    public Object get(String key) {
        return map.get(key);
    }
    public String getString(String key) {
        Object value=map.get(key);
        if(value==null){
            return null;
        }

        if(value instanceof String) {
            return (String)value;
        }else{
            return value+"";
        }
    }
    public List getList(String key) {
        Object value=map.get(key);
        if(value==null){
            return Collections.EMPTY_LIST;
        }
        if(value instanceof List){
            return (List)value;
        }else{
            List<String> list = new ArrayList<>();
            list.add(value + "");
            return list;
        }
    }
    public List<Long> getListLong(String key) {
        Object value=map.get(key);
        if(value==null){
            return Collections.EMPTY_LIST;
        }
        if(value instanceof List){
            List<Object> list = (List) value;
            List<Long> returnList = new ArrayList<>();
            if (list != null) {
                for (Object o : list) {
                    returnList.add(Long.parseLong(o + ""));
                }
            }
            return returnList;
        }else{
            List<Long> list = new ArrayList<>();
            list.add(Long.parseLong(value+""));
            return list;
        }
    }

    public List<Float> getListFloat(String key) {
        Object value=map.get(key);
        if(value==null){
            return Collections.EMPTY_LIST;
        }
        if(value instanceof List){
            List<Object> list = (List) value;
            List<Float> returnList = new ArrayList<>();
            if (list != null) {
                for (Object o : list) {
                    returnList.add(Float.parseFloat(o + ""));
                }
            }
            return returnList;
        }else{
            List<Float> list = new ArrayList<>();
            list.add(Float.parseFloat(value+""));
            return list;
        }
    }

    public int getInt(String key) {
        return Integer.parseInt( map.get(key)+"" );
    }
    /** 값이 없는 경우 MsgException*/
    public int getInt(String key, String exceptionMsg) {
        String value=map.get(key)+"";
        value=value.replaceAll("[^0-9]","");
        if(value.equals("")){
            throw new MsgException(exceptionMsg);
        }
        return Integer.parseInt(value);
    }

    /** 값이 없는 경우 0 return */
    public int getIntZero(String key) {
        if (StringUtils.hasText(map.get(key) + "")) {
            return getInt(key);
        } else {
            return 0;
        }
    }

    /** 값이 없는 경우 0 return */
    public Float getFloatZero(String key) {
        String value=map.get(key) + "";
        if (StringUtils.hasText(value)) {
            return Float.parseFloat(value.replaceAll("[^0-9.]",""));
        } else {
            return 0.0f;
        }
    }
    public long getLong(String key) {
        return getLong(key, false);
    }
    public Long getLong(String key, boolean ignoreExceptions) {
        String value=map.get(key)+"";
        value=value.replaceAll("[^0-9]","");
        if(value.equals("")){
            if(ignoreExceptions){
                return null;
            }else{
                throw new MsgException(key+" 입력 부탁드립니다");
            }
        }
        return Long.parseLong(value);
    }
    public void put(String key, Object value) {
        map.put(key, value);
    }
    public Object remove(String key) {
        return map.remove(key);
    }

    public boolean containsKey(String key){
        return map.containsKey(key);
    }

    public boolean containsValue(Object value){
        return map.containsValue(value);
    }

    public void clear(){
        map.clear();
    }

    public Set<Map.Entry<String, Object>> entrySet(){
        return map.entrySet();
    }

    public void printEntrySet(){
        System.out.println( map.entrySet());
    }

    public Set<String> keySet(){
        return map.keySet();
    }

    public boolean isEmpty(){
        return map.isEmpty();
    }

    public void putAll(Map<? extends String, ?extends Object> m){
        map.putAll(m);
    }

    public Map<String,Object> getMap(){
        return map;
    }

    @Override
    public String toString() {
        return map.toString();
    }

    /**멀티셀렉트 파라미터 변경. 파라미터 없거나 공란은 null,있을 때는 배열로 저장*/
    public void multiSelect(String param_name) {
        Object opt = map.get(param_name);
        if (opt != null) {
            if (opt instanceof String) {
                if (opt.equals("")) {
                    map.remove(param_name);
                } else {
                    String opts[] = { (String) opt };
                    map.put(param_name, opts);
                }
            }
        }
    }
    /**날짜1~날짜2 param setting*/
    public void dateRange(String param_name) {
        dateRange(param_name, false);
    }
    /**날짜1~날짜2 param setting*/
    public void dateRange(String param_name, boolean onlyNumber) {
        String date = (String) map.get(param_name);
        if (date == null || date.indexOf("~")==-1) {
            map.remove(param_name);
        } else {
            String dates[] = date.split("~");
            if (onlyNumber){
                map.put(param_name+"1", dates[0].trim().replaceAll("[^0-9]",""));
                map.put(param_name+"2", dates[1].trim().replaceAll("[^0-9]",""));
            }else{
                map.put(param_name+"1", dates[0].trim());
                map.put(param_name+"2", dates[1].trim());
            }
        }
    }
    public <S> S mapParam(Class<S> classType){

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        modelMapper.addConverter(new ConverterLocalDate(), String.class, LocalDate.class);

        Object map = modelMapper.map(this.map, classType);
        return (S)map;
    }
    public <S> S mapParam(Class<S> classType, MatchingStrategy ms){
        ModelMapper modelMapper = new ModelMapper();
        Configuration configuration = modelMapper.getConfiguration();
        configuration.setAmbiguityIgnored(true);
        configuration.setMatchingStrategy(ms);

        Object map = modelMapper.map(this.map, classType);
        try {
            modelMapper.validate();
        } catch (ValidationException ve) {
            ve.getMessage();
        }
        return (S)map;
    }
    /** [^0-9.] replace. value의 값을 숫자 + . 만 남기기*/
    public String onlyNumber(String key){
        if (map.containsKey(key)) {
            String value=getString(key).replaceAll("[^0-9.]", "");
            map.put(key, value);
            return value;
        }else{
            return null;
        }
    }
    /** 제거 replace. value의 값 중  제거 */
    public String replaceComma(String key){
        String value=getString(key).replaceAll("\\,", "");
        map.put(key, value);
        return value;
    }

    /**LocalDate필수값 아닌경우 String -> LocalDate*/
    public LocalDate parseLocalDate(String key) {
        return parseLocalDate(key, true, null);
    }

    /**LocalDate필수값인경우 String -> LocalDate*/
    public LocalDate parseLocalDate(String key, String exceptionMsg) {
        return parseLocalDate(key, false, exceptionMsg);
    }

    /**paramMap의 String value -> LocalDate로*/
    public LocalDate parseLocalDate(String key, boolean isNullable, String exceptionMsg) {
        if (StringUtils.hasText(getString(key))) {
            String value = getString(key);
            map.put(key, LocalDate.parse(value));
            return LocalDate.parse(value);
        } else {
            if (isNullable) {
                return null;
            } else {
                throw new MsgException(exceptionMsg);
            }
        }
    }
}
