import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class testLesson23 {
    @Test
    public void testPunktThree() {
        int responseCode = 0;
        int numOfRedirects = -1;
        String urlR = "https://playground.learnqa.ru/api/long_redirect";
        while(responseCode != 200) {
            Response response = RestAssured
                    .given()
                    .redirects()
                    .follow(false)
                    .when()
                    .get(urlR)
                    .andReturn();

            responseCode = response.getStatusCode();
            System.out.print("Start URL: " + urlR);
            urlR = response.getHeader("Location");
            System.out.println(" status code: "+ responseCode + " Redirect to URL: " + urlR);
            numOfRedirects++;
        }
        System.out.println("\nNumber of redirects: " + numOfRedirects);
    }
}