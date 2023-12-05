package tests;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import lib.ApiCoreRequests;
import lib.BaseTestCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class UserDeleteTest extends BaseTestCase {
    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();
    @Test
    @Description("Try to delete user by id")
    @DisplayName("Test delete user")
    public void testDeleteUser() {
        //LOGIN
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        Response responseGetAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login", authData);

        // некорректная ссылка на метод удаления пользоваеля - не работает
        Response responsePostDel = apiCoreRequests
                .makeSimpleGetRequest("https://playground.learnqa.ru/api/user/del/2");
        responseGetAuth.prettyPrint();
        responsePostDel.prettyPrint();

    }
}
