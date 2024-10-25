package com.pangpang.util;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/** DB값 JSOn으로 파싱해주는 유틸 클래스*/
public class JsonDB {

    /** parse json */
    public static JSONArray toArr(String dbValue) throws ParseException {
        if(dbValue == null || dbValue.equals("")){
            return new JSONArray();
        }
        return (JSONArray)( new JSONParser().parse(dbValue) );
    }
    /** parse json */
    public static JSONObject toMap(String dbValue) throws ParseException {
        if(dbValue == null || dbValue.equals("")){
            return new JSONObject();
        }
        return (JSONObject)( new JSONParser().parse(dbValue) );
    }

}
