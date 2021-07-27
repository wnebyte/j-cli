package util;

import core.TypeConverter;
import exception.runtime.ParseException;

/**
 * "Default" implementation of the <code>TypeConverter</code> interface.
 */
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
