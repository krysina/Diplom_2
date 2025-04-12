package praktikum.orders;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.EnvConfig;
import praktikum.ingredients.IngredientChecks;
import praktikum.ingredients.IngredientClient;
import praktikum.user.User;
import praktikum.user.UserChecks;
import praktikum.user.UserClient;

import java.util.Map;

public class OrderCreateTest {
    private final UserClient client = new UserClient();
    private final UserChecks check = new UserChecks();
    private final OrderClient orderClient = new OrderClient();
    private final OrderChecks orderChecks = new OrderChecks ();
    private final IngredientClient ingredientClient = new IngredientClient();
    private final IngredientChecks ingredientChecks = new IngredientChecks();
    private String accessToken;
    private User user;
    private String firstIngredient;
    private String secondIngredient;
    private String orderFirstIngredient;
    private String orderSecondIngredient;
    private String orderNumber;

    @Before
    public void setUp() {
        user = User.random();
        ValidatableResponse createResponse = client.createUser(user);
        accessToken = check.created(createResponse);
        ValidatableResponse getResponse = ingredientClient.getAllIngredients();
        // Получение первого ингредиента
        firstIngredient = ingredientChecks.getFirstIngredient(getResponse);

    }

    @After
    public void deleteUser() {
        if (accessToken != null) {
            client.deleteUser(accessToken);
        }
    }

    @Test
    @DisplayName("Создание заказа с авторизацией")
    public void createOrderWithTokenTest () {
        // Добавление 1-го ингредиента в заказ
        Map<String, String[]> ingredientsMap = ingredientClient.addIngredientToOrder(firstIngredient);
        ValidatableResponse orderResponse = orderClient.createOrderWithToken (ingredientsMap, accessToken);
        orderFirstIngredient = orderChecks.getIngredientInOrder(orderResponse);
        //Сверка наличия ингредиента в заказе
        assertEquals(firstIngredient,orderFirstIngredient);
    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    public void createOrderWithoutTokenTest() {
        // Добавление 1-го ингредиента в заказ
        Map<String, String[]> ingredientsMap = ingredientClient.addIngredientToOrder(firstIngredient);
        ValidatableResponse orderResponse = orderClient.createOrderWithoutToken (ingredientsMap);
        orderNumber = orderChecks.getOrderNumberInOrder(orderResponse);
        assertThat(orderNumber, notNullValue());
    }

    @Test
    @DisplayName("Создание заказа с ингредиентами")
    public void createOrderWithTwoIngredientsTest () {
        secondIngredient = ingredientChecks.getSecondIngredient(ingredientClient.getAllIngredients());
        // Добавление 2-х ингредиентов в заказ
        Map<String, String[]> ingredientsMap = ingredientClient.addIngredientsToOrder(firstIngredient, secondIngredient);
        ValidatableResponse orderResponse = orderClient.createOrderWithToken (ingredientsMap, accessToken);
        orderFirstIngredient = orderChecks.getIngredientInOrder(orderResponse);
        orderSecondIngredient = orderChecks.getSecondIngredientInOrder(orderResponse);
        //Сверка наличия ингредиентов в заказе
        assertEquals(firstIngredient,orderFirstIngredient);
        assertEquals(secondIngredient,orderSecondIngredient);
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    public void createOrderWithoutIngredientsTest () {
        ValidatableResponse orderResponse = orderClient.createOrderWithoutIngredients(accessToken);
        orderChecks.notCreatedOrderWithoutIngredients(orderResponse);
    }

    @Test
    @DisplayName("Создание заказа с неверным хешем ингредиентов")
    public void createOrderWithInvalidHashIngredientsTest () {
        Map<String, String[]> ingredientsMap = ingredientClient.addIngredientToOrder(EnvConfig.BAD_INGREDIENT);
        ValidatableResponse orderResponse = orderClient.createOrderWithToken(ingredientsMap,accessToken);
        orderChecks.notCreatedOrderWithInvalidHashIngredients(orderResponse);
    }

}