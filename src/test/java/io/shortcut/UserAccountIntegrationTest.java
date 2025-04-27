package io.shortcut;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.shortcut.dto.UserAccountDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import static io.restassured.RestAssured.given;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserAccountIntegrationTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void init() {
        RestAssured.port = port;
    }

    @Test
    @DirtiesContext
    public void test_shouldRegisterNewUser() {
        given()
                .contentType(ContentType.JSON)
                .body(new UserAccountDTO("valid@email.com", "veryStrongPassword"))

                .when().post("/user/register")

                .then()
                .statusCode(201);

    }

    @Test
    @DirtiesContext
    public void test_whenRegister_UserWithEmailExists_ThenReturnError() {
        given()
                .contentType(ContentType.JSON)
                .body(new UserAccountDTO("valid@email.com", "veryStrongPassword"))

                .when().post("/user/register")

                .then()
                .statusCode(201);

        given()
                .contentType(ContentType.JSON)
                .body(new UserAccountDTO("valid@email.com", "veryStrongPassword"))

                .when().post("/user/register")

                .then()
                .statusCode(409)
                .body(equalTo("User already registered"));

    }

    @Test
    @DirtiesContext
    public void test_shouldAuthenticateExistingUser() {
        given()
                .contentType(ContentType.JSON)
                .body(new UserAccountDTO("valid@email.com", "veryStrongPassword"))

                .when().post("/user/register")

                .then()
                .statusCode(201);

        given()
                .contentType(ContentType.JSON)
                .body(new UserAccountDTO("valid@email.com", "veryStrongPassword"))

                .when().post("/user/auth")

                .then()
                .statusCode(200);
    }

    @Test
    @DirtiesContext
    public void test_whenAuthUser_UserNotExist_ThenReturn401Error() {
        given()
                .contentType(ContentType.JSON)
                .body(new UserAccountDTO("user@notregistered.com", "pass"))

                .when().post("/user/auth")

                .then()
                .statusCode(401)
                .body(equalTo("Invalid credentials"));
    }
}
