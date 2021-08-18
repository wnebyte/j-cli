package com.github.wnebyte.jshell;

import com.github.wnebyte.jshell.exception.runtime.ParseException;
import org.junit.Test;

public class ArrayTypeConverterTest {

    @Test(expected = ParseException.class)
    public void test00() throws Exception {
        TypeConverter<Integer[]> adapter = TypeConverterRepository.getTypeConverter(Integer[].class);
        Integer[] array = adapter.convert("[1 2 3 4 5]");
    }



}
