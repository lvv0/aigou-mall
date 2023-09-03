package com.cskaoyan.converter;

import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class StringToDateConverter implements Converter<String, Date> {
    @SneakyThrows
    @Override
    public Date convert(String s) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(s);
    }
}
