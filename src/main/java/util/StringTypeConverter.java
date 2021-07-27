package util;

import core.TypeConverter;
import exception.runtime.ParseException;

public class StringTypeConverter implements TypeConverter<String> {

    @Override
    public String convert(String value) throws ParseException {
        return value;
    }

    @Override
    public String defaultValue() {
        return null;
    }

    @Override
    public boolean isArray() {
        return false;
    }
}
