package com.shed.springboot.utils.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 自定义日期格式装换器,以便于Java的日期类型与数据库的日期类型进行转换
 */
@Component
public class DateConverter implements Converter<String,Date> {
    @Override
    public Date convert(String source) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(source);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
