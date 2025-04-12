package praktikum.orders;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.ingredients.IngredientChecks;
import praktikum.ingredients.IngredientClient;
import praktikum.user.User;
import praktikum.user.UserChecks;
import praktikum.user.UserClient;

import java.util.Map;

public class OrderGetTest {

    private final UserClient client = new UserClient();
    private final UserChecks check = new UserChecks();
    private final OrderClient orderClient = new OrderClient();
    private final OrderChecks orderChecks = new OrderChecks ();
    private final IngredientClient ingredientClient = new IngredientClient();
    private final IngredientChecks ingredientChecks = new IngredientChecks();
    private String accessToken;
    private User user;
    private String firstIngredient;

    @Before
    public void setUp() {
        user = User.random();
        ValidatableResponse createResponse = client.createUser(user);
        accessToken = check.created(createResponse);
        ValidatableResponse getResponse = ingredientClient.getAllIngredients();
        firstIngredient = ingredientChecks.getFirstIngredient(getResponse);
        Map<String, String[]> ingredientsMap = ingredientClient.addIngredientToOrder(firstIngredient);
        orderClient.createOrderWithToken (ingredientsMap, accessToken);
    }

    @After
    public void deleteUser() {
        if (accessToken != null) {
            client.deleteUser(accessToken);
        }
    }

    @Test
    @DisplayName("Получение заказов конкретного пользователя - авторизованный пользователь")
    public void getOrdersWithTokenTest() {
        ValidatableResponse getOrderResponse = orderClient.getOrdersWithToken (accessToken);
        orderChecks.getOrderWithToken (getOrderResponse);
    }

    @Test
    @DisplayName("Получение заказов конкретного пользователя - неавторизованный пользователь")
    public void getOrdersWithoutTokenTest() {
        ValidatableResponse getOrderResponse = orderClient.getOrdersWithoutToken ();
        orderChecks.notGottenOrderWithoutToken(getOrderResponse);
    }
}