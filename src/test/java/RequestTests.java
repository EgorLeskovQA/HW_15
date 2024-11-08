import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;

public class RequestTests extends TestBase {

    @Test
    void successfulLoginTest() {
        String authData = "{\"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\"}";

        given()
                .body(authData)
                .contentType(JSON)
                .log().uri()
                .when()
                .post("/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void noPasswordTest() {
        String authData = "{\"email\": \"eve.holt@reqres.in\", \"password\": \"\"}";

        given()
                .body(authData)
                .contentType(JSON)
                .log().uri()
                .when()
                .post("/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    void noEmailTest() {
        String authData = "{\"email\": \"\", \"password\": \"cityslicka\"}";

        given()
                .body(authData)
                .contentType(JSON)
                .log().uri()
                .when()
                .post("/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing email or username"));
    }

    @Test
    void createUserTest() {
        String userData = "{\"name\": \"saraev\", \"job\": \"tgk\"}";
        given()
                .body(userData)
                .contentType(JSON)
                .log().uri()
                .when()
                .post("/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("saraev"))
                .body("job", is("tgk"));
    }

    @Test
    void createNoBoodyUserTest() {
        String userData = "";
        given()
                .body(userData)
                .contentType(JSON)
                .log().uri()
                .when()
                .post("/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201);

    }

    @Test
    void successfulGetTest() {
        given()
                .log().uri()
                .get("/unknown")
                .then()
                .log().all()
                .statusCode(200)
                .body("data.year", hasItems(2000, 2001, 2002, 2003, 2004, 2005));
    }
}
