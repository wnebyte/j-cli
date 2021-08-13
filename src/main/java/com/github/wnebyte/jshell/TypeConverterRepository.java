package com.github.wnebyte.jshell;

import com.github.wnebyte.jshell.exception.config.NoSuchTypeConverterException;
import com.github.wnebyte.jshell.exception.runtime.ParseException;
import java.lang.reflect.Array;
import java.util.*;

/**
 * This static class acts as a Repository for implementations of the {@link TypeConverter} interface.
 */
public final class TypeConverterRepository {

    /**
     * Returns the TypeConverter to which the specified Class is mapped,
     * or throws a NoSuchTypeConverterException if there is no mapping for the specified Class.
     * @param cls the Class whose associated TypeConverter is to be returned.
     * @param <T> the Type of the specified Class.
     * @return the TypeConverter to which the specified Class is mapped.
     * @throws NoSuchTypeConverterException if there is no mapping for the specified Class.
     */
    @SuppressWarnings("unchecked")
    public static <T> TypeConverter<T> getTypeConverter(final Class<T> cls)
            throws NoSuchTypeConverterException {
        if (cls == null) {
            throw new IllegalArgumentException(
                    "The specified Class must not be null."
            );
        }
        if (TYPE_CONVERTERS.containsKey(cls)) {
            return (TypeConverter<T>) TYPE_CONVERTERS.get(cls);
        }
        else {
            throw new NoSuchTypeConverterException(
                    "No TypeConverter of the specified Class is registered."
            );
        }
    }

    /**
     * If the specified Class is not already associated with a TypeConverter
     * (or is mapped to <code>null</code>) associates it with the given TypeConverter and returns
     * <code>true</code>, else returns <code>false</code>.
     * @param cls Class which the specified TypeConverter is to be associated.
     * @param typeConverter TypeConverter to be associated with the specified Class.
     * @param <T> Type of the specified Class.
     * @return <code>true</code> if there was no previous mapping for the specified Class,
     * else <code>false</code>.
     */
    public static <T> boolean putIfAbsent(final Class<T> cls, final TypeConverter<T> typeConverter) {
        TypeConverter<?> tc = TYPE_CONVERTERS.putIfAbsent(cls, typeConverter);
        return tc == null;
    }

    public static <T> TypeConverter<T[]> arrayAdapterOf(final Class<T> componentType)
    {
        return new TypeConverter<T[]>() {
            @Override
            @SuppressWarnings("unchecked")
            public T[] convert(String value) throws ParseException {
                try {
                    String[] elements = TypeConverter.arraySplit(value);
                    T[] array = (T[]) Array.newInstance(componentType, elements.length);
                    TypeConverter<T> typeConverter =
                            TypeConverterRepository.getTypeConverter(componentType);
                    int i = 0;
                    for (String element : elements) {
                        String val = TypeConverter.normalize(element);
                        array[i++] = typeConverter.convert(val);
                    }
                    return array;
                } catch (NoSuchTypeConverterException e) {
                    return null;
                }
            }
            @Override
            public T[] defaultValue() {
                return null;
            }
            @Override
            public boolean isArray() {
                return true;
            }
        };
    }

    public static final TypeConverter<Boolean> BOOLEAN_TYPE_CONVERTER = new TypeConverter<Boolean>() {
        @Override
        public Boolean convert(String value) throws ParseException {
            try {
                return Boolean.parseBoolean(value);
            } catch (Exception e) {
                throw new ParseException(e.getMessage(), value);
            }
        }
        @Override
        public Boolean defaultValue() {
            return false;
        }
        @Override
        public boolean isArray() {
            return false;
        }
    };

    public static final TypeConverter<boolean[]> BOOLEAN_ARRAY_TYPE_CONVERTER = new TypeConverter<boolean[]>() {
        @Override
        public boolean[] convert(String value) throws ParseException {
            try {
                String[] elements = TypeConverter.arraySplit(value);
                boolean[] array = new boolean[elements.length];
                int i = 0;
                for (String element : elements) {
                    String val = TypeConverter.normalize(element);
                    array[i++] = BOOLEAN_TYPE_CONVERTER.convert(val);
                }
                return array;
            } catch (Exception e) {
                throw new ParseException(e.getMessage(), value);
            }
        }
        @Override
        public boolean[] defaultValue() {
            return new boolean[0];
        }
        @Override
        public boolean isArray() {
            return true;
        }
    };

    public static final TypeConverter<Byte> BYTE_TYPE_CONVERTER = new TypeConverter<Byte>() {
        @Override
        public Byte convert(String value) throws ParseException {
            try {
                return Byte.parseByte(value);
            } catch (Exception e) {
                throw new ParseException(e.getMessage(), value);
            }
        }
        @Override
        public Byte defaultValue() {
            return (byte) 0;
        }
        @Override
        public boolean isArray() {
            return false;
        }
    };

    public static final TypeConverter<byte[]> BYTE_ARRAY_TYPE_CONVERTER = new TypeConverter<byte[]>() {
        @Override
        public byte[] convert(String value) throws ParseException {
            try {
                String[] elements = TypeConverter.arraySplit(value);
                byte[] array = new byte[elements.length];
                int i = 0;
                for (String element : elements) {
                    String val = TypeConverter.normalize(element);
                    array[i++] = BYTE_TYPE_CONVERTER.convert(val);
                }
                return array;
            } catch (Exception e) {
                throw new ParseException(e.getMessage(), value);
            }
        }
        @Override
        public byte[] defaultValue() {
            return new byte[0];
        }
        @Override
        public boolean isArray() {
            return true;
        }
    };

    public static final TypeConverter<Character> CHARACTER_TYPE_CONVERTER = new TypeConverter<Character>() {
        @Override
        public Character convert(String value) throws ParseException {
            try {
                return value.charAt(0);
            } catch (Exception e) {
                throw new ParseException(e.getMessage(), value);
            }
        }
        @Override
        public Character defaultValue() {
            return '\u0000';
        }
        @Override
        public boolean isArray() {
            return false;
        }
    };

    public static final TypeConverter<char[]> CHAR_ARRAY_TYPE_CONVERTER = new TypeConverter<char[]>() {
        @Override
        public char[] convert(String value) throws ParseException {
            try {
                String[] elements = TypeConverter.arraySplit(value);
                char[] array = new char[elements.length];
                int i = 0;
                for (String element : elements) {
                    String val = TypeConverter.normalize(element);
                    array[i++] = CHARACTER_TYPE_CONVERTER.convert(val);
                }
                return array;
            } catch (Exception e) {
                throw new ParseException(e.getMessage(), value);
            }
        }
        @Override
        public char[] defaultValue() {
            return new char[0];
        }
        @Override
        public boolean isArray() {
            return true;
        }
    };

    public static final TypeConverter<Double> DOUBLE_TYPE_CONVERTER = new TypeConverter<Double>() {
        @Override
        public Double convert(String value) throws ParseException {
            try {
                return Double.parseDouble(value);
            } catch (Exception e) {
                throw new ParseException(e.getMessage(), value);
            }
        }
        @Override
        public Double defaultValue() {
            return 0.0d;
        }
        @Override
        public boolean isArray() {
            return false;
        }
    };

    public static final TypeConverter<double[]> DOUBLE_ARRAY_TYPE_CONVERTER = new TypeConverter<double[]>() {
        @Override
        public double[] convert(String value) throws ParseException {
            try {
                String[] elements = TypeConverter.arraySplit(value);
                double[] array = new double[elements.length];
                int i = 0;
                for (String element : elements) {
                    String val = TypeConverter.normalize(element);
                    array[i++] = DOUBLE_TYPE_CONVERTER.convert(val);
                }
                return array;
            } catch (Exception e) {
                throw new ParseException(e.getMessage(), value);
            }
        }
        @Override
        public double[] defaultValue() {
            return new double[0];
        }
        @Override
        public boolean isArray() {
            return true;
        }
    };

    public static final TypeConverter<Float> FLOAT_TYPE_CONVERTER = new TypeConverter<Float>() {
        @Override
        public Float convert(String value) throws ParseException {
            try {
                return Float.parseFloat(value);
            } catch (Exception e) {
                throw new ParseException(e.getMessage(), value);
            }
        }
        @Override
        public Float defaultValue() {
            return 0.0f;
        }
        @Override
        public boolean isArray() {
            return false;
        }
    };

    public static final TypeConverter<float[]> FLOAT_ARRAY_TYPE_CONVERTER = new TypeConverter<float[]>() {
        @Override
        public float[] convert(String value) throws ParseException {
            try {
                String[] elements = TypeConverter.arraySplit(value);
                float[] array = new float[elements.length];
                int i = 0;
                for (String element : elements) {
                    String val = TypeConverter.normalize(element);
                    array[i++] = FLOAT_TYPE_CONVERTER.convert(val);
                }
                return array;
            } catch (Exception e) {
                throw new ParseException(e.getMessage(), value);
            }
        }
        @Override
        public float[] defaultValue() {
            return new float[0];
        }
        @Override
        public boolean isArray() {
            return true;
        }
    };

    public static final TypeConverter<Integer> INTEGER_TYPE_CONVERTER = new TypeConverter<Integer>() {
        @Override
        public Integer convert(String value) throws ParseException {
            try {
                return Integer.parseInt(value);
            } catch (Exception e) {
                throw new ParseException(e.getMessage(), value);
            }
        }
        @Override
        public Integer defaultValue() {
            return 0;
        }
        @Override
        public boolean isArray() {
            return false;
        }
    };

    public static final TypeConverter<int[]> INT_ARRAY_TYPE_CONVERTER = new TypeConverter<int[]>() {
        @Override
        public int[] convert(String value) throws ParseException {
            try {
                String[] elements = TypeConverter.arraySplit(value);
                int[] array = new int[elements.length];
                int i = 0;
                for (String element : elements) {
                    String val = TypeConverter.normalize(element);
                    array[i++] = INTEGER_TYPE_CONVERTER.convert(val);
                }
                return array;
            } catch (Exception e) {
                throw new ParseException(e.getMessage(), value);
            }
        }
        @Override
        public int[] defaultValue() {
            return new int[0];
        }
        @Override
        public boolean isArray() {
            return true;
        }
    };

    public static final TypeConverter<Long> LONG_TYPE_CONVERTER = new TypeConverter<Long>() {
        @Override
        public Long convert(String value) throws ParseException {
            try {
                return Long.parseLong(value);
            } catch (Exception e) {
                throw new ParseException(e.getMessage(), value);
            }
        }
        @Override
        public Long defaultValue() {
            return 0L;
        }
        @Override
        public boolean isArray() {
            return false;
        }
    };

    public static final TypeConverter<long[]> LONG_ARRAY_TYPE_CONVERTER = new TypeConverter<long[]>() {
        @Override
        public long[] convert(String value) throws ParseException {
            try {
                String[] elements = TypeConverter.arraySplit(value);
                long[] array = new long[elements.length];
                int i = 0;
                for (String element : elements) {
                    String val = TypeConverter.normalize(element);
                    array[i++] = LONG_TYPE_CONVERTER.convert(val);
                }
                return array;
            } catch (Exception e) {
                throw new ParseException(e.getMessage(), value);
            }
        }
        @Override
        public long[] defaultValue() {
            return new long[0];
        }
        @Override
        public boolean isArray() {
            return true;
        }
    };

    public static final TypeConverter<Short> SHORT_TYPE_CONVERTER = new TypeConverter<Short>() {
        @Override
        public Short convert(String value) throws ParseException {
            try {
                return Short.parseShort(value);
            } catch (Exception e) {
                throw new ParseException(e.getMessage(), value);
            }
        }
        @Override
        public Short defaultValue() {
            return (short) 0;
        }
        @Override
        public boolean isArray() {
            return false;
        }
    };

    public static final TypeConverter<short[]> SHORT_ARRAY_TYPE_CONVERTER = new TypeConverter<short[]>() {
        @Override
        public short[] convert(String value) throws ParseException {
            try {
                String[] elements = TypeConverter.arraySplit(value);
                short[] array = new short[elements.length];
                int i = 0;
                for (String element : elements) {
                    String val = TypeConverter.normalize(element);
                    array[i++] = SHORT_TYPE_CONVERTER.convert(val);
                }
                return array;
            } catch (Exception e) {
                throw new ParseException(e.getMessage(), value);
            }
        }
        @Override
        public short[] defaultValue() {
            return new short[0];
        }
        @Override
        public boolean isArray() {
            return true;
        }
    };

    public static final TypeConverter<String> STRING_TYPE_CONVERTER = new TypeConverter<String>() {
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
    };

    public static final TypeConverter<String[]> STRING_ARRAY_TYPE_CONVERTER = new TypeConverter<String[]>() {
        @Override
        public String[] convert(String value) throws ParseException {
            try {
                String[] elements = TypeConverter.arraySplit(value);
                String[] array = new String[elements.length];
                int i = 0;
                for (String element : elements) {
                    String val = TypeConverter.normalize(element);
                    array[i++] = STRING_TYPE_CONVERTER.convert(val);
                }
                return array;
            } catch (Exception e) {
                throw new ParseException(e.getMessage(), value);
            }
        }
        @Override
        public String[] defaultValue() {
            return new String[0];
        }
        @Override
        public boolean isArray() {
            return true;
        }
    };

    private static final Map<Class<?>, TypeConverter<?>> TYPE_CONVERTERS
            = new HashMap<Class<?>, TypeConverter<?>>() {
        {
            put(boolean.class, BOOLEAN_TYPE_CONVERTER);
            put(Boolean.class, BOOLEAN_TYPE_CONVERTER);
            put(boolean[].class, BOOLEAN_ARRAY_TYPE_CONVERTER);
            put(Boolean[].class, arrayAdapterOf(Boolean.class));
            put(byte.class, BYTE_TYPE_CONVERTER);
            put(Byte.class, BYTE_TYPE_CONVERTER);
            put(byte[].class, BYTE_ARRAY_TYPE_CONVERTER);
            put(Byte[].class, arrayAdapterOf(Byte.class));
            put(char.class, CHARACTER_TYPE_CONVERTER);
            put(Character.class, CHARACTER_TYPE_CONVERTER);
            put(char[].class, CHAR_ARRAY_TYPE_CONVERTER);
            put(Character[].class, arrayAdapterOf(Character.class));
            put(double.class, DOUBLE_TYPE_CONVERTER);
            put(Double.class, DOUBLE_TYPE_CONVERTER);
            put(double[].class, DOUBLE_ARRAY_TYPE_CONVERTER);
            put(Double[].class, arrayAdapterOf(Double.class));
            put(float.class, FLOAT_TYPE_CONVERTER);
            put(Float.class, FLOAT_TYPE_CONVERTER);
            put(float[].class, FLOAT_ARRAY_TYPE_CONVERTER);
            put(Float[].class, arrayAdapterOf(Float.class));
            put(int.class, INTEGER_TYPE_CONVERTER);
            put(Integer.class, INTEGER_TYPE_CONVERTER);
            put(int[].class, INT_ARRAY_TYPE_CONVERTER);
            put(Integer[].class, arrayAdapterOf(Integer.class));
            put(long.class, LONG_TYPE_CONVERTER);
            put(Long.class, LONG_TYPE_CONVERTER);
            put(long[].class, LONG_ARRAY_TYPE_CONVERTER);
            put(Long[].class, arrayAdapterOf(Long.class));
            put(short.class, SHORT_TYPE_CONVERTER);
            put(Short.class, SHORT_TYPE_CONVERTER);
            put(short[].class, SHORT_ARRAY_TYPE_CONVERTER);
            put(Short[].class, arrayAdapterOf(Short.class));
            put(String.class, STRING_TYPE_CONVERTER);
            put(String[].class, STRING_ARRAY_TYPE_CONVERTER);
        }
    };
}