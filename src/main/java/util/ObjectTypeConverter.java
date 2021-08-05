package util;

import core.TypeConverter;
import exception.runtime.ParseException;

public class ObjectTypeConverter implements TypeConverter<Object> {

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
