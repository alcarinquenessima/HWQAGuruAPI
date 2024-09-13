import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class RegistrationTest extends Testbase{
    @Test
    public void SuccessfulRegistrationTest() {
        String data = "{\"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\"}";
        given()
                .body(data)
                .contentType(JSON)
                .log().body()
                .when()
                .post("/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("id", is(4))
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }
    @Test
    public void UnsuccessfulRegistrationTest() {
        String data = "{\"email\": \"eve.holt@reqres.in\"}";
        given()
                .body(data)
                .contentType(JSON)
                .log().body()
                .when()
                .post("/register")
                .then()
                .log().status()
                .log().body()
                .body("error", is("Missing password"))
                .statusCode(400);
    }
    @Test
    public void UnsuccessfulRegistrationTest2() {
        String data = "";
        given()
                .body(data)
                .contentType(JSON)
                .log().body()
                .when()
                .post("/register")
                .then()
                .log().status()
                .log().body()
                .body("error", is("Missing email or username"))
                .statusCode(400);
    }
}
