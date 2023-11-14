import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class lesson2_1 {
    @Test
    public void testPunktOne(){

        JsonPath responseJSON = RestAssured
                .get("https://playground.learnqa.ru/api/get_json_homework")
                .jsonPath();

        System.out.println(responseJSON.prettyPrint()); // вывод структуры ответа сайта
        System.out.println("\n Text:\n");
        ArrayList messages = responseJSON.getJsonObject("messages.message");
        ArrayList timestamps = responseJSON.getJsonObject("messages.timestamp");

        System.out.println(messages.get(1)); // вывод текста второго сообщения
        System.out.println(timestamps.get(1));  // вывод временной отметки второго сообщения
    }
}
