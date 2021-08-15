package com.github.wnebyte.jshell.util;

import com.github.wnebyte.jshell.TypeConverter;
import com.github.wnebyte.jshell.exception.runtime.ParseException;

public final class ObjectTypeConverter implements TypeConverter<Object> {

    @Override
    public Object convert(String value) throws ParseException {
        return value;
    }
    @Override
    public Object defaultValue() {
        return null;
    }
    @Override
    public boolean isArray() {
        return false;
    }
}
