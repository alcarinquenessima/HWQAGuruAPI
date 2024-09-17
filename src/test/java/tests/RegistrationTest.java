package tests;

import models.RegistrationModel;
import models.RegistrationResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.ResponseRequestSpecs.*;

@Tag("APITests")
@Tag("Registration_tests")
@DisplayName("Registration tests")
public class RegistrationTest extends TestBase {
    RegistrationModel authData = new RegistrationModel();

    @Test
    public void SuccessfulRegistrationTest() {
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("pistol");

        RegistrationResponseModel response = step("Successful registration", () ->
                given(requestSpec)
                        .body(authData)
                        .when()
                        .post("/register")
                        .then()
                        .spec(responseSpec200)
                        .extract().as(RegistrationResponseModel.class));

        step("Check response", () -> {
            assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
            assertEquals(4, response.getId());
        });
    }

    @Test
    public void UnsuccessfulRegistrationTest() {
        authData.setEmail("eve.holt@reqres.in");

        RegistrationResponseModel response = step("Unsuccessful registration: no password", () ->
                given(requestSpec)
                        .body(authData)
                        .when()
                        .post("/register")
                        .then()
                        .spec(responseSpec400)
                        .extract().as(RegistrationResponseModel.class));

        step("Check response", () ->
                assertEquals("Missing password", response.getError()));
    }

    @Test
    public void UnsuccessfulRegistrationTest2() {
        RegistrationModel authData = new RegistrationModel();
        authData.setEmail("");
        RegistrationResponseModel response = step("Unsuccessful registration: no email", () ->
                given(requestSpec)
                        .body(authData)
                        .when()
                        .post("/register")
                        .then()
                        .spec(responseSpec400)
                        .extract().as(RegistrationResponseModel.class));

        step("Check response", () ->
                assertEquals("Missing email or username", response.getError()));
    }
}
