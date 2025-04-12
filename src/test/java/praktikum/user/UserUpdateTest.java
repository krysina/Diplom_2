package praktikum.user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserUpdateTest {

    private final UserClient client = new UserClient();
    private final UserChecks check = new UserChecks();
    private String accessToken;
    private String accessTokenSecondUser;
    private User user;
    private Credentials creds;
    private User secondUser;

    @Before
    public void createUser() {
        user = User.random();
        ValidatableResponse createResponse = client.createUser(user);
        accessToken = check.created(createResponse);
        creds = Credentials.fromUser(user);
    }

    @After
    public void deleteUser() {
        if (accessToken != null) {
            client.deleteUser(accessToken);
        }
    }

    @Test
    @DisplayName("Изменение email пользователя c авторизацией")
    public void updateUserEmailTest() {
        ValidatableResponse updateResponse = client.updateUserEmail(creds, accessToken);
        check.updateEmailSuccess(updateResponse);
    }

    @Test
    @DisplayName("Изменение email на существующий email другого пользователя")
    public void updateUserExistingEmailTest() {
        // Создаем второго пользователя для тестирования уникальности email
        secondUser = User.random();
        ValidatableResponse secondCreateResponse = client.createUser(secondUser);
        accessTokenSecondUser = check.created(secondCreateResponse);

        // Используем email второго пользователя для проверки уникальности
        creds.setEmail(secondUser.getEmail());
        ValidatableResponse updateResponse = client.updateUserEmailOtherUserExistingEmail(creds, accessToken);
        check.notUpdatedExistingEmail(updateResponse);
        //Удаляем второго пользователя
        client.deleteUser(accessTokenSecondUser);
    }

    @Test
    @DisplayName("Изменение email пользователя без авторизации")
    public void updateUserEmailWithoutTokenTest() {

        ValidatableResponse updateResponse = client.updateUserEmailWithoutToken(creds);
        check.notUpdatedWithoutToken(updateResponse);
    }

    @Test
    @DisplayName("Изменение name пользователя c авторизацией")
    public void updateUserNameTest() {

        ValidatableResponse updateResponse = client.updateUserName(creds, accessToken);
        check.updateNameSuccess(updateResponse);
    }

    @Test
    @DisplayName("Изменение name пользователя без авторизации")
    public void updateUserNameWithoutTokenTest() {

        ValidatableResponse updateResponse = client.updateUserNameWithoutToken(creds);
        check.notUpdatedWithoutToken(updateResponse);
    }

    @Test
    @DisplayName("Изменение password пользователя c авторизацией")
    public void updateUserPasswordTest() {

        ValidatableResponse updateResponse = client.updateUserPassword(creds, accessToken);
        check.updatePasswordSuccess(updateResponse);
        ValidatableResponse loginWithNewPasswordResponse = client.loginUser(creds);
        accessToken = check.loginSuccess(loginWithNewPasswordResponse);
    }

    @Test
    @DisplayName("Изменение password пользователя без авторизации")
    public void updateUserPasswordWithoutTokenTest() {

        ValidatableResponse updateResponse = client.updateUserPasswordWithoutToken(creds);
        check.notUpdatedWithoutToken(updateResponse);
    }

}