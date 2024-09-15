package tests;

import models.RegistrationModel;
import models.RegistrationResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.RegistrationSpec.*;

@Tag("APITests")
@Tag("Registration_tests")
@DisplayName("Registration tests")
public class RegistrationTest extends Testbase{
    RegistrationModel authData = new RegistrationModel();

    @Test
    public void SuccessfulRegistrationTest() {
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword ("pistol");

        RegistrationResponseModel response = step("Successful registration", () ->
         given(RegistrationRequestSpec)
                .body(authData)
        .when()
                .post("/register")
        .then()
                .spec(RegistrationResponseSpec)
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
                given(RegistrationRequestSpec)
                .body(authData)
        .when()
                .post("/register")
        .then()
                .spec(RegistrationResponseSpec2)
                .extract().as(RegistrationResponseModel.class));

        step("Check response", () ->
        assertEquals("Missing password", response.getError()));
    }
    @Test
    public void UnsuccessfulRegistrationTest2() {
        RegistrationModel authData = new RegistrationModel();
        authData.setEmail("");
        RegistrationResponseModel response = step("Unsuccessful registration: no email", () ->
                given(RegistrationRequestSpec)
                .body(authData)
        .when()
                .post("/register")
         .then()
                .spec(RegistrationResponseSpec2)
                .extract().as(RegistrationResponseModel.class));

        step("Check response", () ->
                assertEquals("Missing email or username", response.getError()));
    }
}
