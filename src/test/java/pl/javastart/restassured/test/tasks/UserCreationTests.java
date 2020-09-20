package pl.javastart.restassured.test.tasks;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class UserCreationTests {

    String baseURL = "https://swaggerpetstore.przyklady.javastart.pl/v2/user";

    @Test
    public void givenCorrectUserDataWhenCreateUserThenUserIsCreatedTest() {

        String user = "{\n" +
                "  \"id\": 445,\n" +
                "  \"username\": \"firstuser\",\n" +
                "  \"firstName\": \"Krzysztof\",\n" +
                "  \"lastName\": \"Kowalski\",\n" +
                "  \"email\": \"krzysztof@test.com\",\n" +
                "  \"password\": \"password\",\n" +
                "  \"phone\": \"+123456789\",\n" +
                "  \"userStatus\": 1\n" +
                "}";

        given().log().uri().log().method().body(user)
                .contentType("application/json")
                .when().post(baseURL)
                .then().log().everything().statusCode(200);

        given().log().method().log().uri()
                .contentType("application/json")
                .pathParam("username", "firstuser")
                .when().get(baseURL + "/{username}")
                .then().log().all().statusCode(200);

    }

}
