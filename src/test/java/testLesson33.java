import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class testLesson33 {

    protected String getHeader(Response Response, String name, String value){
        Headers headers = Response.getHeaders();
        assertTrue(headers.hasHeaderWithName(name), "Response doesn`t have header with name" + name);
        assertEquals(headers.getValue(name), value, "Response header value is not equals to " + value);
        return "ok";
    }

    @Test
    public void getHdrs() {
        Map<String, String> listing = new HashMap<>();  // не придумал ничего лучше, как задать набор проверяемых значений
        listing.put("Content-Type", "application/json");
        listing.put("Content-Length", "15");
        listing.put("Connection", "keep-alive");
        listing.put("Keep-Alive", "timeout=10");
        listing.put("Server", "Apache");
        listing.put("x-secret-homework-header", "Some secret value");
        listing.put("Cache-Control", "max-age=0");
        //listing.put("Date","Wed, 22 Nov 2023 18:04:28 GMT");  // Закомментировал, т.к. точно подсчитать дату
        //listing.put("Expires","Wed, 22 Nov 2023 18:04:28 GMT");  // до секунд, чтобы сошлось с ответом - сложно пока
        Response response = RestAssured
                .post("https://playground.learnqa.ru/api/homework_header")
                .andReturn();
        //System.out.println(response.getHeaders());  // одним глазом посмотреть на состав заголовков и их значения
        for (Map.Entry entry : listing.entrySet()) {
            System.out.print("\nChecking header: " + entry.getKey() + " , value: " + entry.getValue());
            System.out.print(" - " + getHeader(response, entry.getKey().toString(), entry.getValue().toString()));
        }
    }
}
