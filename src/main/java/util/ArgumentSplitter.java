package util;

import core.TypeConverter;

public final class ArgumentSplitter {

    private String name;

    private String value;

    private String val;

    public final ArgumentSplitter setName(final String name) {
        this.name = name;
        return this;
    }

    public final ArgumentSplitter setValue(final String value) {
        this.value = value;
        this.val = value;
        return this;
    }

    public final ArgumentSplitter split() {
        if (value == null) {
            throw new IllegalArgumentException(
                    "Value must be non null."
            );
        }
        if (name != null) {
            val = value.split(name.concat("\\s"), 2)[1];
        }
        char firstChar = val.charAt(0);
        char lastChar = val.charAt(val.length() - 1);

        if (!(firstChar == '"' && lastChar == '"') && !(firstChar == '[' && lastChar == ']')) {
            val = val.split("\\s", 2)[0];
        }
        return this;
    }

    public final ArgumentSplitter normalize(final boolean isArray) {
        val = isArray ?
                TypeConverter.normalizeArray(val) : TypeConverter.normalize(val);
        return this;
    }

    public final String get() {
        return val;
    }
}
