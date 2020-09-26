package pl.javastart.restassured.test.tasks;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;
import pl.javastart.main.pojo.User;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UserUpdateTest extends BaseTest {

    @Test
    public void givenCorrectUserDataWhenFirstNameLastNameAreUpdatedThenUserDataIsUpdatedTest() {

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setContentType("application/json");
        RequestSpecification defaultRequestSpecification = requestSpecBuilder.build();

        User user = new User();
        user.setId("445");
        user.setUserName("firstuser");
        user.setFirstName("Krzysztof");
        user.setLastName("Kowalski");
        user.setEmail("krzysztof@test.com");
        user.setPassword("password");
        user.setPhone("+123456789");
        user.setUserStatus("1");

        ResponseSpecBuilder postResponseSpecBuilder = new ResponseSpecBuilder();
        postResponseSpecBuilder
                .expectBody("code", equalTo(200))
                .expectBody("type", equalTo("unknown"))
                .expectBody("message", equalTo("445"))
                .expectStatusCode(200);
        ResponseSpecification userCreationResponseSpecification = postResponseSpecBuilder.build();

        given().spec(defaultRequestSpecification)
                .body(user)
                .when().post("user")
                .then().assertThat().spec(userCreationResponseSpecification);

        user.setFirstName("Janusz");
        user.setLastName("Nowak");

        given().spec(defaultRequestSpecification)
                .pathParam("username", user.getUserName())
                .body(user)
                .when().put("user/{username}")
                .then().assertThat().spec(userCreationResponseSpecification);

        ResponseSpecBuilder updateResponseSpecBuilder = new ResponseSpecBuilder();
        updateResponseSpecBuilder
                .expectBody("id", equalTo("445"))
                .expectBody("username", equalTo("firstuser"))
                .expectBody("firstName", equalTo("Janusz"))
                .expectBody("lastName", equalTo("Nowak"))
                .expectBody("email", equalTo("krzysztof@test.com"))
                .expectBody("password", equalTo("password"))
                .expectBody("phone", equalTo("+123456789"))
                .expectBody("userStatus", equalTo("1"))
                .expectStatusCode(200);
        ResponseSpecification getResponseSpecification = updateResponseSpecBuilder.build();

        given().spec(defaultRequestSpecification)
                .pathParam("username", user.getUserName())
                .when().get("user/{username}")
                .then().assertThat().spec(getResponseSpecification);
    }
}

