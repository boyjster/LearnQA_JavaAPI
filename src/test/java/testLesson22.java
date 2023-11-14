import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class testLesson22 {
    @Test
    public void testPunktTwo() {

        Response response = RestAssured
                .given()
                .redirects()
                .follow(false)
                .when()
                .get("https://playground.learnqa.ru/api/long_redirect")
                .andReturn();

        String header = response.getHeader("Location");
        System.out.println("Redirect URL: " + header);

    }
}
