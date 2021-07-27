import util.StringTypeConverter;
import core.TypeConverter;
import core.TypeConverterRepository;
import exception.runtime.ParseException;
import core.Person;
import org.junit.Assert;
import org.junit.Test;

import static util.ReflectionUtil.*;

public class TypeConverterTest {

    @Test
    public void test00() throws Exception
    {
        var adapter = new TypeConverter<Person>() {
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
        };

        var bool = TypeConverterRepository.putIfAbsent(Integer.class, TypeConverterRepository.INTEGER_TYPE_CONVERTER);
        Assert.assertFalse(bool);
    }

    @Test
    public void test01() throws Exception {
        Class<?> myClass = StringTypeConverter.class;


        Object object = newInstance(myClass);

    }
}
