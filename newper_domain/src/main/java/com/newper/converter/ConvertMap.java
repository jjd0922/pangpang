package com.newper.converter;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Converter(autoApply = true)
public class ConvertMap implements AttributeConverter<Map, String> {

    @Override
    public String convertToDatabaseColumn(Map attribute) {
        return JSONObject.toJSONString(attribute);
    }

    @Override
    public Map convertToEntityAttribute(String dbData) {
        try{
            return (Map) new JSONParser().parse(dbData);
        }catch (Exception e){
            return new HashMap();
        }
    }
}
