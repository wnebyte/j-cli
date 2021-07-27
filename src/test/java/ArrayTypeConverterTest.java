import convert.TypeConverterRepository;
import org.junit.Test;

public class ArrayTypeConverterTest {

    @Test
    public void test00() throws Exception {
        var adapter = TypeConverterRepository.getTypeConverter(Integer[].class);
        Integer[] array = adapter.convert("[1 2 3 4 5]");
    }



}
