package test;

import core.TypeConverter;
import exception.runtime.ParseException;

public class PersonTypeConverter implements TypeConverter<Person> {

    @Override
    public Person convert(String value) throws ParseException {
        return null;
    }

    @Override
    public Person defaultValue() {
        return null;
    }

    @Override
    public boolean isArray() {
        return false;
    }
}
