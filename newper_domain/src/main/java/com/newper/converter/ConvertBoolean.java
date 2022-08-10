package com.newper.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ConvertBoolean implements AttributeConverter<Boolean, Byte> {

    @Override
    public Byte convertToDatabaseColumn(Boolean attribute) {
        if(attribute != null && attribute){
            return 1;
        }else{
            return 0;
        }
    }

    @Override
    public Boolean convertToEntityAttribute(Byte dbData) {
        return (dbData != null && dbData == 1);
    }
}
