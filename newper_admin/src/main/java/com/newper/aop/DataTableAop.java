package com.newper.aop;

import com.newper.constant.basic.EnumOption;
import io.lettuce.core.ScriptOutputType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.*;

@Aspect
@Component
public class DataTableAop {

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
            }
        }
        map.putAll(addMap);
    }


}
