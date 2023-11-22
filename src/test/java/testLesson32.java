import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class testLesson32 {

    @Test
    public void getCookie(){
        String name = "HomeWork";
        Response response = RestAssured
                .post("https://playground.learnqa.ru/api/homework_cookie")
                .andReturn();
        Map<String, String> cookies = response.getCookies();
        //System.out.println(response.getCookies());
        assertTrue(cookies.containsKey(name), "Response doesn`t have cookie with name " + name);
        assertEquals("hw_value",cookies.get(name), "Cookie value is not equals to 'hw_value'");
    }
}
