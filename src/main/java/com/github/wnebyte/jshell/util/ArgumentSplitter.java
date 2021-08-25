package com.github.wnebyte.jshell.util;

import com.github.wnebyte.jshell.TypeConverter;

/**
 * This class declares methods for splitting a String.
 */
public final class ArgumentSplitter {

    private String name;

    private String value;

    private String val;

    /**
     * Sets the name or delimiter.
     * @param name the name or delimiter.
     * @return this ArgumentSplitter.
     */
    public final ArgumentSplitter setName(final String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the value or content.
     * @param value the value or content.
     * @return this ArgumentSplitter.
     */
    public final ArgumentSplitter setValue(final String value) {
        this.value = value;
        this.val = value;
        return this;
    }

    /**
     * Splits the specified <code>value</code> using the specified <code>name</code> as a delimiter.
     * @return this ArgumentSplitter.
     */
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

    /**
     * Normalizes the result of the split.
     * @param isArray whether the Argument is an array.
     * @return this ArgumentSplitter.
     */
    public final ArgumentSplitter normalize(final boolean isArray) {
        val = isArray ? TypeConverter.normalizeArray(val) : TypeConverter.normalize(val);
        return this;
    }

    /**
     * Returns the result
     * @return the result.
     */
    public final String get() {
        return val;
    }
}