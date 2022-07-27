package com.github.wnebyte.jcli;

import com.github.wnebyte.jarguments.adapter.TypeAdapter;
import com.github.wnebyte.jarguments.exception.TypeConversionException;

public class StubTypeAdapter implements TypeAdapter<Object> {

    @Override
    public Object convert(String s) throws TypeConversionException {
        return null;
    }

    @Override
    public Object defaultValue() {
        return null;
    }
}
