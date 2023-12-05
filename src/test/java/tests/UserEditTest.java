package tests;

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lib.ApiCoreRequests;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserEditTest extends BaseTestCase {
    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();
    @Test
    @Description("Create new user, login, edit user info")
    @DisplayName("Test create and check user")
    public void testEditJustCreatedTes(){
        //GENERATE USER
        Map<String, String> userData = DataGenerator.getRegistrationData();

        JsonPath responseCreateAuth = apiCoreRequests
                .makePostJSPath("https://playground.learnqa.ru/api/user/", userData);

        String userId = responseCreateAuth.getString("id");

        //LOGIN
        Map<String, String> authData = new HashMap<>();
        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));

        Response responseGetAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login", authData);

        //EDIT
        String newName = "Changed Name";
        Map<String, String> editData = new HashMap<>();
        editData.put("firstName", newName);

        Response responseEditUser = RestAssured
                .given()
                .header("x-csrf-token", this.getHeader(responseGetAuth, "x-csrf-token"))
                .cookie("auth_sid", this.getCookie(responseGetAuth, "auth_sid"))
                .body(editData)
                .put("https://playground.learnqa.ru/api/user/" + userId)
                .andReturn();

        //GET
        Response responseUserData = RestAssured
                .given()
                .header("x-csrf-token", this.getHeader(responseGetAuth, "x-csrf-token"))
                .cookie("auth_sid", this.getCookie(responseGetAuth, "auth_sid"))
                .get("https://playground.learnqa.ru/api/user/" + userId)
                .andReturn();

        Assertions.assertJsonByName(responseUserData, "firstName", newName);
    }

    @Test
    @Description("Try editing user without authorisation")
    @DisplayName("Negative test edit user, no authorisation")
    public void testEditUserNonAuthorised(){
        //GENERATE USER
        Map<String, String> userData = DataGenerator.getRegistrationData();

        JsonPath responseCreateAuth = apiCoreRequests
                .makePostJSPath("https://playground.learnqa.ru/api/user/", userData);

        String userId = responseCreateAuth.getString("id");
        String oldName = userData.get("username");

        //NO LOGIN

        //EDIT
        String newName = "Changed userName";
        Map<String, String> editData = new HashMap<>();
        editData.put("username", newName);

        Response responseEditUser = apiCoreRequests
                .makePutRequest("https://playground.learnqa.ru/api/user/" + userId, editData);

        //GET
        Response responseUserData = apiCoreRequests
                .makeSimpleGetRequest("https://playground.learnqa.ru/api/user/" + userId);

        Assertions.assertJsonByName(responseUserData, "username", oldName);
    }

    @Description("Create new user, login, edit user info with incorrect values")
    @DisplayName("Negative test create and edit user")
    @ParameterizedTest
    @CsvSource({"email, ChangedEmail.ru", "firstName, C"})
    public void testEditJustCreatedTesNegative(String parameter, String value){
        //GENERATE USER
        Map<String, String> userData = DataGenerator.getRegistrationData();

        JsonPath responseCreateAuth = apiCoreRequests
                .makePostJSPath("https://playground.learnqa.ru/api/user/", userData);

        String userId = responseCreateAuth.getString("id");

        //LOGIN
        Map<String, String> authData = new HashMap<>();
        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));

        Response responseGetAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login", authData);

        //EDIT
        String newEmail = "ChangedEmail.su";
        Map<String, String> editData = new HashMap<>();
        editData.put(parameter, value);

        Response responseEditUser = RestAssured
                .given()
                .header("x-csrf-token", this.getHeader(responseGetAuth, "x-csrf-token"))
                .cookie("auth_sid", this.getCookie(responseGetAuth, "auth_sid"))
                .body(editData)
                .put("https://playground.learnqa.ru/api/user/" + userId)
                .andReturn();

        if(parameter.equals("email")){
            assertEquals("<html>\n" +
                    "  <body>Invalid email format</body>\n" +
                    "</html>", responseEditUser.asPrettyString(),"Invalid server answer, parameter: " + parameter);
        }
        if(parameter.equals("firstName")){
            assertEquals("{\n" +
                    "    \"error\": \"Too short value for field firstName\"\n" +
                    "}", responseEditUser.asPrettyString(),"Invalid server answer, parameter: " + parameter);
        }
    }


    @Test
    @Description("Create new user, login, edit another user")
    @DisplayName("Negative test, edit user under logged from another user")
    public void testEditJustCreatedFromAnoterAcc() {
        //GENERATE USER
        Map<String, String> userData = DataGenerator.getRegistrationData();

        JsonPath responseCreateAuth = apiCoreRequests
                .makePostJSPath("https://playground.learnqa.ru/api/user/", userData);

        String userId = responseCreateAuth.getString("id");

        //LOGIN
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        Response responseGetAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login", authData);
        Assertions.assertResponseTextEquals(responseGetAuth, "{\"user_id\":2}");

        //EDIT
        Map<String, String> editData = new HashMap<>();
        editData.put("email", "Changed@Email.su");

        Response responseEditUser = RestAssured
                .given()
                //.header("x-csrf-token", this.getHeader(responseGetAuth, "x-csrf-token"))
                //.cookie("auth_sid", this.getCookie(responseGetAuth, "auth_sid"))
                .body(editData)
                .put("https://playground.learnqa.ru/api/user/" + userId)
                .andReturn();
        //System.out.println(responseEditUser.asString());
        Assertions.assertResponseTextEquals(responseEditUser,"Auth token not supplied");
    }

}
