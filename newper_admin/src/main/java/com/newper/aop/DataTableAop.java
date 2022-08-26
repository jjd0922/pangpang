package com.newper.aop;

import com.newper.component.SessionInfo;
import com.newper.constant.basic.EnumOption;
import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.util.ExcelDownload;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
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

    /** key: column명 (snake_case) , value : enum class명(camelBack) */
    Map<String,String> enumClasses = new HashMap<>();

//    @PostConstruct
//    public void printCode() throws ClassNotFoundException{
//        File enumDir = new File(ClassLoader.getSystemResource("com/newper/constant").getFile());
//        System.out.println("====start====  \n");
//        for (String s : enumDir.list()) {
//            if(s.endsWith(".class")){
//                s = s.replace(".class", "");
//                System.out.println("enumClasses.put(\""+s.replaceAll(("([A-Z])"),"_$1").substring(1).toLowerCase()+"\",\""+s+"\");");
//            }
//
//        }
//        System.out.println("\n\n  ====end====  \n"+enumDir.list().length);
//    }

    @PostConstruct
    public void postConstruct() {

        enumClasses.put("po_buy_product_type","PType1");
        enumClasses.put("po_sell_channel","Channel");
        enumClasses.put("gs_rank","GRank");


        //36개
        enumClasses.put("cate_display","CateDisplay");
        enumClasses.put("cate_spec","CateSpec");
        enumClasses.put("cate_type","CateType");
        enumClasses.put("cc_cal_type","CcCalType");
        enumClasses.put("cc_cycle","CcCycle");
        enumClasses.put("cc_fee_type","CcFeeType");
        enumClasses.put("cc_state","CcState");
        enumClasses.put("cc_type","CcType");
        enumClasses.put("cf_type","CfType");
        enumClasses.put("channel","Channel");
        enumClasses.put("ci_insurance_state","CiInsuranceState");
        enumClasses.put("ci_type","CiType");
        enumClasses.put("com_state","ComState");
        enumClasses.put("com_type","ComType");
        enumClasses.put("ct_type","CtType");
        enumClasses.put("giftg_state","GiftgState");
        enumClasses.put("gift_state","GiftState");
        enumClasses.put("g_rank","GRank");
        enumClasses.put("g_stock_state","GStockState");
        enumClasses.put("hw_state","HwState");
        enumClasses.put("ig_state","IgState");
        enumClasses.put("loc_state","LocState");
        enumClasses.put("menu_type","MenuType");
        enumClasses.put("pe_state","PeState");
        enumClasses.put("pn_process","PnProcess");
        enumClasses.put("po_state","PoState");
        enumClasses.put("po_type","PoType");
        enumClasses.put("p_state","PState");
        enumClasses.put("p_type1","PType1");
        enumClasses.put("p_type2","PType2");
        enumClasses.put("p_type3","PType3");
        enumClasses.put("s_state","SState");
        enumClasses.put("u_state","UState");
        enumClasses.put("u_type","UType");
        enumClasses.put("wh_state","WhState");
        enumClasses.put("loc_type","LocType");
        enumClasses.put("loc_form","LocForm");
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
                    addMap.put(key + "_STR", getEnumOption(key.toLowerCase(), (String)value));
                }
            }else if(key.indexOf("_LIST") != -1){
                //제외
                if(key.equals("CATE_SPEC_LIST")){
                    continue;
                }

                int indexOfList = key.lastIndexOf("_LIST");
                String columnName = key.substring(0, indexOfList).toLowerCase();
                if (enumClasses.containsKey(columnName)) {
                    Object value = map.get(key);
                    if(value instanceof String){
                        String[] enumList = ((String) value).split(",");
                        String dtValue = "";
                        for (String s : enumList) {
                            dtValue+=getEnumOption(columnName, s)+", ";
                        }
                        addMap.put(key + "", dtValue.substring(0,dtValue.length()-2));
                    }

                }

            }
        }
        map.putAll(addMap);
    }
    /** enumClass이름과 name으로 option 가져오기*/
    private String getEnumOption(String enumClass, String name){
        try{
            EnumOption[] enumConstants = (EnumOption[])Class.forName("com.newper.constant." + enumClasses.get(enumClass)).getEnumConstants();
            EnumOption o = Arrays.stream(enumConstants).filter(en -> {
                return en.toString().equals(name);
            }).findFirst().get();
            return o.getOption();
        }catch (ClassNotFoundException ce){
            return name;
        }catch (NoSuchElementException nse){
            System.out.println(enumClass+"\t"+name+"\t no enum value");
            throw nse;
        }
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

            //argsResolver에서 세팅
            response = (HttpServletResponse) paramMap.get("response");
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
