import core.TypeConverter;
import core.TypeConverterRepository;
import exception.runtime.ParseException;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

public class TypeConverterTest {

    @Test
    public void test00() throws Exception
    {
        var adapter = new TypeConverter<UUID>() {
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

}
