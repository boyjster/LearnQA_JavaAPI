import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

public class lesson2_2 {
    @Test
    public void testPunktTwo(){

        JsonPath responseJSON = RestAssured
                .get("https://playground.learnqa.ru/api/long_redirect")
                .jsonPath();

        System.out.println(responseJSON.prettyPrint())

}
