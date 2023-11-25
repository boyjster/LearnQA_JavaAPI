import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static groovy.xml.Entity.not;
import static org.apache.commons.lang3.StringUtils.length;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class testLesson34 {

    static Stream<Arguments> dataArray() {
        return Stream.of(
                Arguments.of("Mozilla/5.0 (Linux; U; Android 4.0.2; en-us; Galaxy Nexus Build/ICL53F) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30", "Mobile", "No", "Android"),
                Arguments.of("Mozilla/5.0 (iPad; CPU OS 13_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) CriOS/91.0.4472.77 Mobile/15E148 Safari/604.1", "Mobile", "Chrome", "iOS"),
                Arguments.of("Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)", "Googlebot", "Unknown", "Unknown"),
                Arguments.of("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.77 Safari/537.36 Edg/91.0.100.0", "Web", "Chrome", "No"),
                Arguments.of("Mozilla/5.0 (iPad; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1", "Mobile", "No", "iPhone")
        );
    }
    @ParameterizedTest
    @MethodSource("dataArray")
    public void getInfo(String userAg, String platform, String browser, String device) {
        Map<String, String> headerUserAgent = new HashMap<>();
        headerUserAgent.put("User-Agent", userAg);
        JsonPath response = RestAssured
                .given()
                .headers(headerUserAgent)
                .get("https://playground.learnqa.ru/api/user_agent_check")
                .jsonPath();
        //response.prettyPrint();
        String result = "";
        if(!response.get("platform").equals(platform)){
            result = result + "expected platform '" + platform +
                    "' is not equal to actual value '" + response.get("platform") + "' ";
        }
        if(!response.get("browser").equals(browser)){
            result = result + "expected browser '" + browser +
                    "' is not equal to actual value '" + response.get("browser") + "' ";
        }
        if(!response.get("device").equals(device)){
            result = result + "expected device '" + device +
                    "' is not equal to actual value '" + response.get("device") + "' ";
        }
        if(length(result)>1){
            System.out.println("For User-Agent '" + userAg + "' " + result);
        }
    }
}