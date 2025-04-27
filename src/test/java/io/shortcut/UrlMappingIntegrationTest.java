package io.shortcut;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.ValidatableResponse;
import io.shortcut.dto.UrlMappingRequestDTO;
import io.shortcut.dto.UserAccountDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import static io.restassured.RestAssured.given;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UrlMappingIntegrationTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void init() {
        RestAssured.port = port;
    }

    @Test
    @DirtiesContext
    public void test_AuthenticatedUser_CreatesUrlMapping_Return200Ok() {
        String redirectUrl = "https://some.url.com";

        given()
                .contentType(ContentType.JSON)
                .body(new UserAccountDTO("valid@email.com", "veryStrongPassword"))

                .when().post("/user/register")

                .then()
                .statusCode(HttpStatus.CREATED.value());

        var authResponse = given()
                .contentType(ContentType.JSON)
                .body(new UserAccountDTO("valid@email.com", "veryStrongPassword"))

                .when().post("/user/auth")

                .then()
                .statusCode(HttpStatus.OK.value());

        String jwtToken = authResponse.extract().body().asString();

        given()
                .contentType(ContentType.JSON)
                .header(new Header("Authorization", "Bearer " + jwtToken))
                .body(new UrlMappingRequestDTO(redirectUrl))

                .when().put("/mappings")

                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DirtiesContext
    public void test_WhenUnauthenticatedUser_CreateUrlMapping_Return403Error() {
        given()
                .contentType(ContentType.JSON)
                .body(new UrlMappingRequestDTO("https://www.google.com"))

                .when().post("/mappings")

                .then()
                .statusCode(403);
    }

    //test user can create multiple mappings and
    //test user can only see own mappings
    //test error when invalid url (not matching regex)
}
