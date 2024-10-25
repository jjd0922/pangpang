package com.pangpang.converter;

import org.modelmapper.AbstractConverter;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

public class ConvertLocalDate extends AbstractConverter<String, LocalDate> {
    @Override
    public LocalDate convert(String source) {
        if (StringUtils.hasText(source)) {
            LocalDate ld = LocalDate.parse(source);
            return ld;
        } else {
            return null;
        }
    }
}
