package com.github.wnebyte.jshell;

import com.github.wnebyte.jshell.exception.runtime.ParseException;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

public class TypeConverterTest {

    @Test
    public void test00() throws Exception
    {
        TypeConverter<UUID> adapter = new TypeConverter<UUID>() {
            @Override
            public UUID convert(String value) throws ParseException {
                return null;
            }

            @Override
            public UUID defaultValue() {
                return null;
            }

            @Override
            public boolean isArray() {
                return false;
            }
        };

        boolean bool = TypeConverterRepository
                .putIfAbsent(Integer.class, TypeConverterRepository.INTEGER_TYPE_CONVERTER);
        Assert.assertFalse(bool);
    }

    @Test
    public void test01() throws Exception {
        TypeConverter<int[]> typeConverter = TypeConverterRepository.INT_ARRAY_TYPE_CONVERTER;

    }

}
