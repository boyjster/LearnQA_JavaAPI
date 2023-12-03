package tests;

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.Assertions;
import lib.ApiCoreRequests;
import lib.BaseTestCase;
import lib.DataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

public class UserRegisterTest extends BaseTestCase {

    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();
    @Test
    @Description("This test negative create user with existing email")
    @DisplayName("Test negative create user")
    public void testCreateUserWithExistingEmail(){
        String email = "vinkotov@example.com";

        Map<String, String> userData = new HashMap<>();
        userData.put("email", email);
        userData = DataGenerator.getRegistrationData(userData);

        //System.out.println("from method testCreateUserWithExistingEmail: " + userData);
        Response responseGetAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/", userData);

        Assertions.assertResponseCodeEquals(responseGetAuth, 400);
        Assertions.assertResponseTextEquals(responseGetAuth, "Users with email '" + email + "' already exists");
    }

    @Test
    @Description("This test succesfully create user")
    @DisplayName("Test positive create user")
    public void testCreateUserSuccessfully(){
        String email = DataGenerator.getRandomEmail();
        Map<String, String> userData = DataGenerator.getRegistrationData();

        //System.out.println("from method testCreateUserSuccessfully: " + userData);

        Response responseCreateAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/", userData);

        Assertions.assertResponseCodeEquals(responseCreateAuth, 200);
        Assertions.assertJsonHasField(responseCreateAuth, "id");
    }

    @Test
    @Description("This test create user with incorrect email")
    @DisplayName("Test negative create user")
    public void testCreateUserWithWrongEmail(){
        String email = "vinkotovexample.com";

        Map<String, String> userData = new HashMap<>();
        userData.put("email", email);
        userData = DataGenerator.getRegistrationData(userData);

        Response responseGetAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/", userData);

        Assertions.assertResponseCodeEquals(responseGetAuth, 400);
        Assertions.assertResponseTextEquals(responseGetAuth, "Invalid email format");
    }
    @Description("This test create user without one parameter")
    @DisplayName("Negative test create user")
    @ParameterizedTest
    @ValueSource(strings = {"email", "password", "username", "firstName", "lastName"})
    public void testCreateUserWithWrongParam(String parameter){
        Map<String, String> userData = new HashMap<>();
        userData.put(parameter, null);
        userData = DataGenerator.getRegistrationData(userData);

        Response responseGetCreate = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/", userData);

        Assertions.assertResponseCodeEquals(responseGetCreate, 400);
        Assertions.assertResponseTextEquals(responseGetCreate, "The following required params are missed: " + parameter);
    }

    @Test
    @Description("This test create user with short firstName")
    @DisplayName("Test negative create user")
    public void testCreateUserWithShortName(){
        String firstName = "a";

        Map<String, String> userData = new HashMap<>();
        userData.put("firstName", firstName);
        userData = DataGenerator.getRegistrationData(userData);

        Response responseGetAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/", userData);

        Assertions.assertResponseCodeEquals(responseGetAuth, 400);
        Assertions.assertResponseTextEquals(responseGetAuth, "The value of 'firstName' field is too short");
    }

    @Test
    @Description("This test create user with long firstName")
    @DisplayName("Test negative create user")
    public void testCreateUserWithLongName(){
        String firstName = "abcdefghigklmnopqrsruvwxyz1234567890abcdefghigklmnopqrsruvwxyz1234567890abcdefghigklmnopqrsruvwxyz1234567890abcdefghigklmnopqrsruvwxyz1234567890abcdefghigklmnopqrsruvwxyz1234567890abcdefghigklmnopqrsruvwxyz1234567890abcdefghigklmnopqrsruvwxyz1234567890";

        Map<String, String> userData = new HashMap<>();
        userData.put("firstName", firstName);
        userData = DataGenerator.getRegistrationData(userData);

        Response responseGetAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/", userData);

        Assertions.assertResponseCodeEquals(responseGetAuth, 400);
        Assertions.assertResponseTextEquals(responseGetAuth, "The value of 'firstName' field is too long");
    }
}
