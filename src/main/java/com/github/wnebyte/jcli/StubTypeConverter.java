package com.github.wnebyte.jcli;

import com.github.wnebyte.jarguments.convert.TypeConverter;
import com.github.wnebyte.jarguments.exception.ParseException;

public class StubTypeConverter implements TypeConverter<Object> {

    @Override
    public Object convert(String s) throws ParseException {
        return null;
    }

    @Override
    public Object defaultValue() {
        return null;
    }
}
