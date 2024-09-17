package tests;

import models.UserInfoModel;
import models.UserInfoResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.EditUserSpec.*;

@Tag("APITests")
@Tag("Edit_user_info")
@DisplayName("Change user info")
public class EditUserInfoTest extends TestBase {
    UserInfoModel authData = new UserInfoModel();

    @Test
    public void updateFullInfoInDatabaseTest() {
        authData.setName("Ratty");
        authData.setJob("rat");

        UserInfoResponseModel response = step("Change the name and job", () ->
                given(requestSpec)
                        .body(authData)
                        .when()
                        .put("/users/2")
                        .then()
                        .spec(responseSpec200)
                        .extract().as(UserInfoResponseModel.class));

        step("Check response", () -> {
            assertEquals("Ratty", response.getName());
            assertEquals("rat", response.getJob());
            assertThat(response.getUpdatedAt(), startsWith(String.valueOf(LocalDate.now())));
        });
    }

    @Test
    public void updateNameInDatabaseTest() {
        authData.setName("Ratty");

        UserInfoResponseModel response = step("Change only name", () ->
                given(requestSpec)
                        .body(authData)
                        .when()
                        .put("/users/2")
                        .then()
                        .spec(responseSpec200)
                        .extract().as(UserInfoResponseModel.class));

        step("Check response", () -> {
            assertEquals("Ratty", response.getName());
            assertThat(response.getJob(), nullValue());
            assertThat(response.getUpdatedAt(), startsWith(String.valueOf(LocalDate.now())));
        });
    }

    @Test
    public void deleteUserTest() {
        step("Delete user", () -> {
            given(requestSpec)
                    .when()
                    .delete("/users/2")
                    .then()
                    .spec(editUserResponseSpec2);
        });
    }

}
