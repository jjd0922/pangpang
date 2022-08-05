package com.newper.aop;

import com.newper.component.SessionInfo;
import com.newper.constant.basic.EnumOption;
import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.util.ExcelDownload;
import io.lettuce.core.ScriptOutputType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;

@Aspect
@Component
public class DataTableAop {

    @Autowired
    private SessionInfo sessionInfo;

    /**
     * key: column명 (snake_case) , value : enum class명(camelBack)
     */
    HashMap<String, EnumOption[]> enumClasses = new HashMap<>();

    @PostConstruct
    public void postConstruct() throws ClassNotFoundException{

        File enumDir = new File(ClassLoader.getSystemResource("com/newper/constant").getFile());
        for (String s : enumDir.list()) {
            if(!s.equals("basic")){
                s = s.replace(".class", "");

                Object[] enumConstants = Class.forName("com.newper.constant." + s).getEnumConstants();
                if(enumConstants instanceof EnumOption[]){
                    enumClasses.put(s.replaceAll("([a-z])([A-Z]+)","$1_$2").toLowerCase(), (EnumOption[])enumConstants);
                }
            }
        }
    }

    @Around("execution(* com.newper.mapper.*.*(..)))")
    public Object roundMapper(ProceedingJoinPoint jp) throws Throwable{
        Object[] params=jp.getArgs();
        Object result=jp.proceed(params);
//        Map<String, Object> map = new HashMap<>();
        if (result != null) {
            if(result instanceof List){
                List resultList = (List) result;
                if(!resultList.isEmpty()){
                    if( resultList.get(0) instanceof Map){
                        for (Object resultListObject : resultList) {
                            setEnumOption((Map)resultListObject);
                        }
                    }
                }
            }else if(result instanceof Map){
                setEnumOption((Map)result);
            }
        }
        return result;
    }



    /** map enum 보여지는 값 세팅 */
    private void setEnumOption(Map<String,Object> map) {

        Map<String, Object> addMap = new HashMap<>();
        for (String key : map.keySet()) {
            if(enumClasses.containsKey(key.toLowerCase())){
                Object value = map.get(key);
                if(value instanceof String){
                    EnumOption enumOption = Arrays.stream(enumClasses.get(key.toLowerCase())).filter(en -> {
                        return en.toString().equals((String) value);
                    }).findFirst().get();
                    addMap.put(key + "_STR", enumOption.getOption());
                }
            }else if(key.indexOf("_LIST") != -1){
                int indexOfList = key.lastIndexOf("_LIST");
                String columnName = key.substring(0, indexOfList).toLowerCase();
                if (enumClasses.containsKey(columnName)) {
                    Object value = map.get(key);
                    if(value instanceof String){
                        String[] enumList = ((String) value).split(",");
                        String dtValue = "";
                        for (String s : enumList) {
                            EnumOption enumOption = Arrays.stream(enumClasses.get(columnName)).filter(en -> {
                                return en.toString().equals((String) s);
                            }).findFirst().get();
                            dtValue+=enumOption.getOption()+",";
                        }
                        addMap.put(key + "_STR", dtValue.substring(0,dtValue.length()-1));
                    }

                }

            }
        }
        map.putAll(addMap);
    }

    @Around("execution(com.newper.dto.ReturnDatatable com.newper.controller.rest.*.*(..)))")
    public Object roundController(ProceedingJoinPoint jp) throws Throwable{
        Object[] params=jp.getArgs();

        ParamMap paramMap = null;
        boolean isDownload = false;
        HttpServletResponse response = null;
        for (Object param : params) {
            if(param instanceof ParamMap){
                paramMap = (ParamMap) param;
                isDownload = paramMap.containsKey("download");
            }else if(param instanceof HttpServletResponse){
                response = (HttpServletResponse) param;
            }
        }
        ReturnDatatable result=(ReturnDatatable)jp.proceed(params);

        if(isDownload){
            // excel 다운로드에서 엑셀 header, value List
            String thead[]=((String)paramMap.get("thead")).split(";");
            String cols[]=((String)paramMap.get("cols")).split(";");
            List<String[]> columns=new ArrayList<String[]>();
            for(int i=0;i<cols.length;i++) {
                String[] column={thead[i].trim(),cols[i].trim()};
                columns.add(column);
            }

            ExcelDownload.createExcel(response, result.getFileName(), columns, result.getData());
            return null;
        }

//        Map<String, Object> map = new HashMap<>();
//        if (result != null && result) {
//            if(result instanceof List){
//                List resultList = (List) result;
//                if(!resultList.isEmpty()){
//                    if( resultList.get(0) instanceof Map){
//                        for (Object resultListObject : resultList) {
//                            setEnumOption((Map)resultListObject);
//                        }
//                    }
//                }
//            }else if(result instanceof Map){
//                setEnumOption((Map)result);
//            }
//        }
        return result;
    }

}
