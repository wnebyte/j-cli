package com.github.wnebyte.jshell;

import org.junit.Test;

public class ArrayTypeConverterTest {

    @Test
    public void test00() throws Exception {
        TypeConverter<Integer[]> adapter = TypeConverterRepository.getTypeConverter(Integer[].class);
        Integer[] array = adapter.convert("[1 2 3 4 5]");
    }



}
