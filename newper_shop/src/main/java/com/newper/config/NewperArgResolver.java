package com.newper.config;

import com.newper.dto.ParamMap;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

public class NewperArgResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if(parameter.getParameterType() == String.class){
            return true;
        }else if(parameter.getParameterType() == String[].class){
            return true;
        }else if(parameter.getParameterType() == ParamMap.class){
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        if(parameter.getParameterType() == String.class){
            return filteredString( request.getParameterValues(parameter.getParameterName()) );
        }else if(parameter.getParameterType() == String[].class){
            List<String> temp =(List<String>)filteredString(request.getParameterValues(parameter.getParameterName()));
            String[] filteredList = new String[temp.size()];
            for (int i=0;i<temp.size();i++){
                filteredList[i] = temp.get(i);
            }
            return filteredList;
        }else if(parameter.getParameterType() == ParamMap.class){
            ParamMap paramMap = new ParamMap();
            Enumeration<?> enumeration = request.getParameterNames();

            String key = null;
            String[] values = null;

            while (enumeration.hasMoreElements()) {
                key = (String) enumeration.nextElement();
                values = request.getParameterValues(key);
                if (values != null) {
                    if (key.equals("start") || key.equals("length")) {
                        paramMap.put(key, Integer.parseInt(values[0]));
                    }else if(key.equals("search[value]")){
                        String keyword = filteredString(values[0]);
                        if(!keyword.equals("")){
                            paramMap.put("keyword", "%"+keyword+"%");
                        }
                    }else if(key.equals("pathUrl")){
                        paramMap.put(key, values[0].replaceAll(request.getContextPath(), ""));
                    }else {
                        paramMap.put(key, filteredString(values));
                    }
                }
            }

            return paramMap;
        }


        return null;
    }

    private Object filteredString(String str[]){
        if(str==null){
            return "";
        }
        if(str.length==1){
            return filteredString(str[0]);
        }

        List<String> filteredList_arr = Arrays.stream(str).map(this::filteredString).collect(Collectors.toList());
        return filteredList_arr;
    }
    private String filteredString(String str){
        if(str==null){
            return "";
        }
        String value = HtmlUtils.htmlEscape(str+"");
//        .replaceAll("<", "&lt;");
//        value = value.replaceAll("%", "&#37;");
//        value = value.replaceAll(System.getProperty("line.separator"), "<br>");
//        value = value.replaceAll("\n", "<br>");

        return value.trim();
    }
}