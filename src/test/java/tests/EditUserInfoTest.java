import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;


public class EditUserInfoTest extends Testbase{
    @Test
    public void updateFullInfoInDatabaseTest() {
        String data = "{\"name\": \"Ratty\", \"job\": \"rat\"}";
        given()
                .body(data)
                .contentType(JSON)
                .log().body()
                .when()
                .put("/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("name", is("Ratty"))
                .body("job", is("rat"))
                .body("updatedAt", startsWith(String.valueOf(LocalDate.now())));
    }

    @Test
    public void updateNameInDatabaseTest() {
        String data = "{\"name\": \"Ratty\"}";
        given()
                .body(data)
                .contentType(JSON)
                .log().body()
                .when()
                .put("/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("name", is("Ratty"))
                .body("job", nullValue())
                .body("updatedAt", startsWith(String.valueOf(LocalDate.now())));
    }
    @Test
    public void deleteUserTest() {
        given()
                .log().body()
                .when()
                .delete("/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(204);
    }

}
