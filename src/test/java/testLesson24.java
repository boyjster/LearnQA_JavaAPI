import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class testLesson24 {
    private static void wait(int seconds){
        System.out.println("Wait for " + seconds + " seconds");
        try {
            Thread.sleep(1000 * seconds);
        }catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private static String getstatus(String token, String targetURL){
        Map<String, String> param = new HashMap<>();
        param.put("token", token);
        JsonPath response = RestAssured
                .given()
                .queryParams(param)
                .when()
                .get(targetURL)
                .jsonPath();
        String status = response.getJsonObject("status");
        //System.out.println("Status: " + status);
        String result = response.getJsonObject("result");
        if(Objects.equals(status, "Job is ready")){
            System.out.println("Status is ok: " + status);
            System.out.println("Result: " + result);
        } else{
            System.out.println("Status NOT ok: " + status);
        }
        return result; // возвращаем результат при необходимости дальнейшего использования
    }

    @Test
    public void Main() {
        String targetURL = "https://playground.learnqa.ru/ajax/api/longtime_job";
        JsonPath response = RestAssured
                .get(targetURL)
                .jsonPath();
        String token = response.getJsonObject("token");
        int timeout = response.getJsonObject("seconds"); // получили время выполнения задачи
        System.out.println("Task is created.\ntoken: " + token);
        System.out.println("Task timeout: " + timeout + " seconds");
        timeout = timeout - 5; // вычисление промежуточного времени для первого запроса
        wait(5); // ожидаем 5 секунд
        String result = getstatus(token, targetURL); // первый запрос
        wait(timeout); // ожидаем остаток времени
        result = getstatus(token, targetURL); // второй запрос
    }
}
