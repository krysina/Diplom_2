package praktikum.user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

public class UserLoginTest {

    private final UserClient client = new UserClient();
    private final UserChecks check = new UserChecks();
    private String accessToken;
    private User user;

    @Before
    public void createUser() {
        user = User.random();
        ValidatableResponse createResponse = client.createUser(user);
        accessToken = check.created(createResponse);
    }

    @After
    public void deleteUser() {
        if (accessToken != null) {
            client.deleteUser(accessToken);
        }
    }

    @Test
    @DisplayName("Логин под существующим пользователем")
    public void loginUserTest() {
        var creds = Credentials.fromUser(user);
        ValidatableResponse loginResponse = client.loginUser(creds);
        accessToken = check.loginSuccess(loginResponse);
        assertThat(accessToken, notNullValue());
    }

    @Test
    @DisplayName("Авторизация c неверным логином")
    public void loginUserWithModifiedEmailTest() {
        var creds = Credentials.fromUser(user);
        ValidatableResponse loginResponse = client.loginWithModifiedEmail(creds);
        check.notLoggedWithModifiedEmail(loginResponse);
    }

    @Test
    @DisplayName("Авторизация c неверным паролем")
    public void loginUserWithModifiedPasswordTest() {
        var creds = Credentials.fromUser(user);
        ValidatableResponse loginResponse = client.loginWithModifiedPassword(creds);
        check.notLoggedWithModifiedPassword(loginResponse);
    }

}