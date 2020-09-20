package pl.javastart.restassured.test.tasks;

import org.testng.annotations.Test;
import pl.javastart.main.pojo.User;

import static io.restassured.RestAssured.given;

public class UserUpdateTest {

    String baseURL = "https://swaggerpetstore.przyklady.javastart.pl/v2/user";

    @Test
    public void givenCorrectUserDataWhenFirstNameLastNameAreUpdatedThenUserDataisUpdatedTest() {

        User user = new User();
        user.setId("445");
        user.setUserName("firstuser");
        user.setFirstName("Krzysztof");
        user.setLastName("Kowalski");
        user.setEmail("krzysztof@test.com");
        user.setPassword("password");
        user.setPhone("+123456789");
        user.setUserStatus("1");

        given().log().all()
                .contentType("application/json")
                .body(user)
                .when().post(baseURL)
                .then().log().everything().statusCode(200);

        user.setFirstName("Janusz");
        user.setLastName("Nowak");

        given().log().all()
                .contentType("application/json")
                .pathParam("username", user.getUserName())
                .body(user)
                .when().put(baseURL + "/{username}")
                .then().log().all().statusCode(200);

        given().log().all()
                .contentType("application/json")
                .pathParam("username", user.getUserName())
                .when().get(baseURL + "/{username}")
                .then().log().all().statusCode(200);

    }
}
