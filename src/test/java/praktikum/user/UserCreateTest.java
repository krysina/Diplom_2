package praktikum.user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.notNullValue;

public class UserCreateTest {

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
    @DisplayName("Создание уникального пользователя")
    public void createUserTest(){
       assertThat(accessToken, notNullValue());
    }

    @Test
    @DisplayName("Создание пользователя, который уже зарегистрирован")
    public void createDoubleUserTest() {
        ValidatableResponse doubleCreateResponse = client.createUser(user);
        check.doubleCreated(doubleCreateResponse);
    }

    @Test
    @DisplayName("Создание пользователя без заполнения обязательного поля email")
    public void createUserWithoutEmailTest() {
       ValidatableResponse createResponse = client.createUserWithoutEmail(user);
        check.notCreatedWithoutEmail(createResponse);
    }

    @Test
    @DisplayName("Создание пользователя без заполнения обязательного поля password")
    public void createUserWithoutPasswordTest() {
        ValidatableResponse createResponse = client.createUserWithoutPassword(user);
        check.notCreatedWithoutPassword(createResponse);
    }

    @Test
    @DisplayName("Создание пользователя без заполнения обязательного поля name")
    public void createUserWithoutNameTest() {
        ValidatableResponse createResponse = client.createUserWithoutName(user);
        check.notCreatedWithoutName(createResponse);
    }
}