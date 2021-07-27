package core;

import exception.config.NoSuchTypeConverterException;
import exception.runtime.ParseException;
import java.lang.reflect.Array;
import java.util.*;

public final class TypeConverterRepository {

    @SuppressWarnings("unchecked")
    public static <T> TypeConverter<T> getTypeConverter(final Class<T> typeOf) throws NoSuchTypeConverterException {
        if (typeOf == null) {
            throw new IllegalArgumentException(
                    "The specified class must not be null."
            );
        }
        if (TYPE_CONVERTERS.containsKey(typeOf)) {
            return (TypeConverter<T>) TYPE_CONVERTERS.get(typeOf);
        }
        else {
            throw new NoSuchTypeConverterException("No TypeConverter of the specified type is known.");
        }
    }

    public static <T> boolean putIfAbsent(final Class<T> typeOf, final TypeConverter<T> typeConverter) {
        TypeConverter<?> tc = TYPE_CONVERTERS.putIfAbsent(typeOf, typeConverter);
        return tc == null;
    }

    public static <T> TypeConverter<T[]> arrayAdapterOf(final Class<T> componentType)
    {
        return new TypeConverter<>() {
            @Override
            @SuppressWarnings("unchecked")
            public T[] convert(String value) throws ParseException {
                try {
                    String[] elements =
                            TypeConverter.normalize(value).split(TypeConverter.ARRAY_ELEMENT_SEPARATOR);
                    T[] array = (T[]) Array.newInstance(componentType, elements.length);
                    TypeConverter<T> typeConverter =
                            TypeConverterRepository.getTypeConverter(componentType);
                    int i = 0;
                    for (String element : elements) {
                        array[i++] = typeConverter.convert(element);
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

    public static final TypeConverter<Boolean> BOOLEAN_TYPE_CONVERTER = new TypeConverter<>() {
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

    public static final TypeConverter<boolean[]> BOOLEAN_ARRAY_TYPE_CONVERTER = new TypeConverter<>() {
        @Override
        public boolean[] convert(String value) throws ParseException {
            try {
                String[] elements =
                        TypeConverter.normalize(value).split(TypeConverter.ARRAY_ELEMENT_SEPARATOR);
                boolean[] array = new boolean[elements.length];
                int i = 0;
                for (String element : elements) {
                    array[i++] = BOOLEAN_TYPE_CONVERTER.convert(element);
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

    public static final TypeConverter<Byte> BYTE_TYPE_CONVERTER = new TypeConverter<>() {
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

    public static final TypeConverter<byte[]> BYTE_ARRAY_TYPE_CONVERTER = new TypeConverter<>() {
        @Override
        public byte[] convert(String value) throws ParseException {
            try {
                String[] elements =
                        TypeConverter.normalize(value).split(TypeConverter.ARRAY_ELEMENT_SEPARATOR);
                byte[] array = new byte[elements.length];
                int i = 0;
                for (String element : elements) {
                    array[i++] = BYTE_TYPE_CONVERTER.convert(element);
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

    public static final TypeConverter<Character> CHARACTER_TYPE_CONVERTER = new TypeConverter<>() {
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

    public static final TypeConverter<char[]> CHAR_ARRAY_TYPE_CONVERTER = new TypeConverter<>() {
        @Override
        public char[] convert(String value) throws ParseException {
            try {
                String[] elements =
                        TypeConverter.normalize(value).split(TypeConverter.ARRAY_ELEMENT_SEPARATOR);
                char[] array = new char[elements.length];
                int i = 0;
                for (String element : elements) {
                    array[i++] = CHARACTER_TYPE_CONVERTER.convert(element);
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

    public static final TypeConverter<Double> DOUBLE_TYPE_CONVERTER = new TypeConverter<>() {
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

    public static final TypeConverter<double[]> DOUBLE_ARRAY_TYPE_CONVERTER = new TypeConverter<>() {
        @Override
        public double[] convert(String value) throws ParseException {
            try {
                String[] elements = TypeConverter.normalize(value).split(TypeConverter.ARRAY_ELEMENT_SEPARATOR);
                double[] array = new double[elements.length];
                int i = 0;
                for (String element : elements) {
                    array[i++] = DOUBLE_TYPE_CONVERTER.convert(element);
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

    public static final TypeConverter<Float> FLOAT_TYPE_CONVERTER = new TypeConverter<>() {
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

    public static final TypeConverter<float[]> FLOAT_ARRAY_TYPE_CONVERTER = new TypeConverter<>() {
        @Override
        public float[] convert(String value) throws ParseException {
            try {
                String[] elements =
                        TypeConverter.normalize(value).split(TypeConverter.ARRAY_ELEMENT_SEPARATOR);
                float[] array = new float[elements.length];
                int i = 0;
                for (String element : elements) {
                    array[i++] = FLOAT_TYPE_CONVERTER.convert(element);
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

    public static final TypeConverter<Integer> INTEGER_TYPE_CONVERTER = new TypeConverter<>() {
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

    public static final TypeConverter<int[]> INT_ARRAY_TYPE_CONVERTER = new TypeConverter<>() {
        @Override
        public int[] convert(String value) throws ParseException {
            try {
                String[] elements =
                        TypeConverter.normalize(value).split(TypeConverter.ARRAY_ELEMENT_SEPARATOR);
                int[] array = new int[elements.length];
                int i = 0;
                for (String element : elements) {
                    array[i++] = INTEGER_TYPE_CONVERTER.convert(element);
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

    public static final TypeConverter<Long> LONG_TYPE_CONVERTER = new TypeConverter<>() {
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

    public static final TypeConverter<long[]> LONG_ARRAY_TYPE_CONVERTER = new TypeConverter<>() {
        @Override
        public long[] convert(String value) throws ParseException {
            try {
                String[] elements =
                        TypeConverter.normalize(value).split(TypeConverter.ARRAY_ELEMENT_SEPARATOR);
                long[] array = new long[elements.length];
                int i = 0;
                for (String element : elements) {
                    array[i++] = LONG_TYPE_CONVERTER.convert(element);
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

    public static final TypeConverter<Short> SHORT_TYPE_CONVERTER = new TypeConverter<>() {
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

    public static final TypeConverter<short[]> SHORT_ARRAY_TYPE_CONVERTER = new TypeConverter<>() {
        @Override
        public short[] convert(String value) throws ParseException {
            try {
                String[] elements =
                        TypeConverter.normalize(value).split(TypeConverter.ARRAY_ELEMENT_SEPARATOR);
                short[] array = new short[elements.length];
                int i = 0;
                for (String element : elements) {
                    array[i++] = SHORT_TYPE_CONVERTER.convert(element);
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

    public static final TypeConverter<String> STRING_TYPE_CONVERTER = new TypeConverter<>() {
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

    public static final TypeConverter<String[]> STRING_ARRAY_TYPE_CONVERTER = new TypeConverter<>() {
        @Override
        public String[] convert(String value) throws ParseException {
            try {
                String[] elements =
                        TypeConverter.normalize(value).split(TypeConverter.ARRAY_ELEMENT_SEPARATOR);
                String[] array = new String[elements.length];
                int i = 0;
                for (String element : elements) {
                    array[i++] = STRING_TYPE_CONVERTER.convert(element);
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

    private static final Map<Class<?>, TypeConverter<?>> TYPE_CONVERTERS = new HashMap<>() {
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