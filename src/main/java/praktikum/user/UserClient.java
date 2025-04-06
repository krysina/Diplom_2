package praktikum.user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import praktikum.Client;
import praktikum.EnvConfig;

public class UserClient extends Client {

    private static final String REGISTER = "/auth/register";
    private static final String LOGIN = "/auth/login";
    private static final String USER = "/auth/user";

    @Step("Создание пользователя")
    public ValidatableResponse createUser(User user) {
        return spec()
                .body(user)
                .when()
                .post(REGISTER)
                .then().log().all();
    }

    @Step("Создание пользователя без поля email")
    public ValidatableResponse createUserWithoutEmail(User user) {
        user.setEmail(null);
        return spec()
                .body(user)
                .post(REGISTER)
                .then().log().all();
    }
    @Step("Создание пользователя без поля password")
    public ValidatableResponse createUserWithoutPassword(User user) {
        user.setPassword(null);
        return spec()
                .body(user)
                .post(REGISTER)
                .then().log().all();
    }

    @Step("Создание пользователя без поля name")
    public ValidatableResponse createUserWithoutName(User user) {
        user.setName(null);
        return spec()
                .body(user)
                .post(REGISTER)
                .then().log().all();
    }

    @Step("Логин пользователя")
    public ValidatableResponse loginUser(Credentials creds) {
        return spec()
                .body(creds)
                .post(LOGIN)
                .then().log().all();
    }

    @Step("Логин пользователя с неверным email")
    public ValidatableResponse loginWithModifiedEmail(Credentials creds) {
        creds.setEmail(EnvConfig.MODIFIED_EMAIL);
        return spec()
                .body(creds)
                .post(LOGIN)
                .then().log().all();
    }

    @Step("Логин пользователя с неверным password")
    public ValidatableResponse loginWithModifiedPassword(Credentials creds) {
        creds.setPassword(EnvConfig.MODIFIED_PASSWORD);
        return spec()
                .body(creds)
                .post(LOGIN)
                .then().log().all();
    }


    @Step("Изменение email пользователя c авторизацией")
    public ValidatableResponse updateUserEmail(Credentials creds, String accessToken) {
        creds.setEmail(EnvConfig.MODIFIED_EMAIL);
        return spec()
                .header(EnvConfig.HEADER_AUTHORIZATION, accessToken)
                .body(creds)
                .patch(USER)
                .then().log().all();
    }

    @Step("Изменение email пользователя на существующий email c авторизацией")
    public ValidatableResponse updateUserEmailOtherUserExistingEmail(Credentials creds, String accessToken) {
        return spec()
                .header(EnvConfig.HEADER_AUTHORIZATION, accessToken)
                .body(creds)
                .patch(USER)
                .then().log().all();
    }

    @Step("Изменение email пользователя без авторизации")
    public ValidatableResponse updateUserEmailWithoutToken(Credentials creds) {
        creds.setEmail(EnvConfig.MODIFIED_EMAIL);
        return spec()
                .body(creds)
                .patch(USER)
                .then().log().all();
    }

    @Step("Изменение name пользователя c авторизацией")
    public ValidatableResponse updateUserName(Credentials creds, String accessToken) {
        creds.setName(EnvConfig.MODIFIED_NAME);
        return spec()
                .header(EnvConfig.HEADER_AUTHORIZATION, accessToken)
                .body(creds)
                .patch(USER)
                .then().log().all();
    }

    @Step("Изменение name пользователя без авторизации")
    public ValidatableResponse updateUserNameWithoutToken(Credentials creds) {
        creds.setName(EnvConfig.MODIFIED_NAME);
        return spec()
                .body(creds)
                .patch(USER)
                .then().log().all();
    }

    @Step("Изменение password пользователя c авторизацией")
    public ValidatableResponse updateUserPassword(Credentials creds, String accessToken) {
        creds.setPassword(EnvConfig.MODIFIED_PASSWORD);
        return spec()
                .header(EnvConfig.HEADER_AUTHORIZATION, accessToken)
                .body(creds)
                .patch(USER)
                .then().log().all();
    }

    @Step("Изменение password пользователя без авторизации")
    public ValidatableResponse updateUserPasswordWithoutToken(Credentials creds) {
        creds.setName(EnvConfig.MODIFIED_PASSWORD);
        return spec()
                .body(creds)
                .patch(USER)
                .then().log().all();
    }

    @Step("Удаление пользователя")
    public ValidatableResponse deleteUser(String accessToken) {
        return spec()
                .header(EnvConfig.HEADER_AUTHORIZATION, accessToken)
                .delete(USER)
                .then().log().all();
    }

}
