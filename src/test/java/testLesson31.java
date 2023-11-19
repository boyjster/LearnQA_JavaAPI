import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class testLesson31 {
    @ParameterizedTest
    @ValueSource(strings = {"123456qwe rtyu", "divk3jf ckfndkc", "ockw, x fe socee"})
    public void checkLength(String text){
        assertTrue(text.length() > 15, "String '" + text + "' length not less than 15 characters");
    }
}
