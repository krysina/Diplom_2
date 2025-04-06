package praktikum.user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import praktikum.EnvConfig;

import java.net.HttpURLConnection;

import static org.hamcrest.CoreMatchers.equalTo;

public class UserChecks {

    @Step("успешно создан пользователь")
    public String created(ValidatableResponse createResponse) {
        String accessToken = createResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .and().body(EnvConfig.SUCCESS, equalTo(true))
                .extract()
                .path("accessToken")
                ;
        return accessToken;
    }
    @Step("Пользователь не создан дублированием")
    public void doubleCreated(ValidatableResponse doubleCreateResponse) {
        doubleCreateResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_FORBIDDEN)
                .and().body(EnvConfig.SUCCESS, equalTo(false))
                .and().body(EnvConfig.MESSAGE, equalTo(EnvConfig.TEXT_MESSAGE_USER_EXISTS));

    }

    @Step("Пользователь не создан без заполнения поля email")
    public void notCreatedWithoutEmail(ValidatableResponse createResponse) {
        createResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_FORBIDDEN)
                .and().body(EnvConfig.SUCCESS, equalTo(false))
                .and().body(EnvConfig.MESSAGE, equalTo(EnvConfig.TEXT_MESSAGE_REQUIRED_FIELDS));

    }

    @Step("Пользователь не создан без заполнения поля password")
    public void notCreatedWithoutPassword(ValidatableResponse createResponse) {
        createResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_FORBIDDEN)
                .and().body(EnvConfig.SUCCESS, equalTo(false))
                .and().body(EnvConfig.MESSAGE, equalTo(EnvConfig.TEXT_MESSAGE_REQUIRED_FIELDS));

    }

    @Step("Пользователь не создан без заполнения поля name")
    public void notCreatedWithoutName(ValidatableResponse createResponse) {
        createResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_FORBIDDEN)
                .and().body(EnvConfig.SUCCESS, equalTo(false))
                .and().body(EnvConfig.MESSAGE, equalTo(EnvConfig.TEXT_MESSAGE_REQUIRED_FIELDS));

    }

    @Step("успешный логин")
    public String loginSuccess(ValidatableResponse loginResponse) {
        String accessToken = loginResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .and().body(EnvConfig.SUCCESS, equalTo(true))
                .extract()
                .path("accessToken")
                ;
        return accessToken;
    }

    @Step("Неуспешный логин с невалидным email")
    public void notLoggedWithModifiedEmail(ValidatableResponse loginResponse) {
        loginResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED)
                .and().body(EnvConfig.SUCCESS, equalTo(false))
                .and().body(EnvConfig.MESSAGE, equalTo(EnvConfig.TEXT_MESSAGE_INCORRECT_DATA));

    }

    @Step("неуспешный логин с невалидным password")
    public void notLoggedWithModifiedPassword(ValidatableResponse loginResponse) {
        loginResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED)
                .and().body(EnvConfig.SUCCESS, equalTo(false))
                .and().body(EnvConfig.MESSAGE, equalTo(EnvConfig.TEXT_MESSAGE_INCORRECT_DATA));

    }

    @Step("Успешное изменение email пользователя с авторизацией")
    public void updateEmailSuccess(ValidatableResponse updateResponse) {
        updateResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .and().body(EnvConfig.SUCCESS, equalTo(true))
                .and().body(EnvConfig.EMAIL, equalTo(EnvConfig.MODIFIED_EMAIL));

    }

    @Step("Успешное изменение name пользователя с авторизацией")
    public void updateNameSuccess(ValidatableResponse updateResponse) {
        updateResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .and().body(EnvConfig.SUCCESS, equalTo(true))
                .and().body(EnvConfig.NAME, equalTo(EnvConfig.MODIFIED_NAME));

    }

    @Step("Неуспешное изменение email пользователя на существующий с авторизацией")
    public void notUpdatedExistingEmail(ValidatableResponse updateResponse) {
        updateResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_FORBIDDEN)
                .and().body(EnvConfig.SUCCESS, equalTo(false))
                .and().body(EnvConfig.MESSAGE, equalTo(EnvConfig.TEXT_MESSAGE_EMAIL_EXISTS));

    }

    @Step("Успешное изменение password пользователя с авторизацией")
    public void updatePasswordSuccess(ValidatableResponse updateResponse) {
        updateResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .and().body(EnvConfig.SUCCESS, equalTo(true));
    }

    @Step("Неуспешное изменение данных пользователя без авторизации")
    public void notUpdatedWithoutToken(ValidatableResponse updateResponse) {
        updateResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED)
                .and().body(EnvConfig.SUCCESS, equalTo(false))
                .and().body(EnvConfig.MESSAGE, equalTo(EnvConfig.TEXT_MESSAGE_NOT_AUTHORISED));

    }
}
