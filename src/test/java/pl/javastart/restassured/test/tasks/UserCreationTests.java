package pl.javastart.restassured.test.tasks;

import org.testng.annotations.Test;
import pl.javastart.main.pojo.User;

import static io.restassured.RestAssured.given;

public class UserCreationTests {

    String baseURL = "https://swaggerpetstore.przyklady.javastart.pl/v2/user";

    @Test
    public void givenCorrectUserDataWhenCreateUserThenUserIsCreatedTest() {

        User user1 = new User();
        user1.setId("445");
        user1.setUserName("firstuser");
        user1.setFirstName("Krzysztof");
        user1.setLastName("Kowalski");
        user1.setEmail("krzysztof@test.com");
        user1.setPassword("password");
        user1.setPhone("+123456789");
        user1.setUserStatus("1");

        given().log().uri().log().all()
                .contentType("application/json")
                .body(user1)
                .when().post(baseURL)
                .then().log().everything().statusCode(200);

        given().log().all()
                .contentType("application/json")
                .pathParam("username", user1.getUserName())
                .when().get(baseURL + "/{username}")
                .then().log().all().statusCode(200);
    }
}
