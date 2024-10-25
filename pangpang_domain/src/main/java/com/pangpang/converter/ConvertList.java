package com.pangpang.converter;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.ArrayList;
import java.util.List;

@Converter(autoApply = true)
public class ConvertList implements AttributeConverter<List, String> {

        @Override
        public String convertToDatabaseColumn(List attribute) {
            return JSONArray.toJSONString(attribute);
        }

        @Override
        public List convertToEntityAttribute(String dbData) {
            try{
                return (List)new JSONParser().parse(dbData);
            }catch (Exception e){
                return new ArrayList();
            }
        }
}
