import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapper;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class lesson2 {
    @Test
    public void testPunktOne(){

        JsonPath responseJSON = RestAssured
                .get("https://playground.learnqa.ru/api/get_json_homework")
                .jsonPath();

        ArrayList msgs = responseJSON.getJsonObject("messages");


        Object msg = msgs.get(1);

        //ObjectMapper msg =
        //Map<String, String> text = msgs.get(1);

        System.out.println(Class);
        System.out.println(msg);
        System.out.println(text);
        //System.out.println(text.get("message"));

    }
}
