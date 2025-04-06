package praktikum.orders;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import praktikum.Client;
import praktikum.EnvConfig;

import java.util.Map;

public class OrderClient extends Client {

    private static final String ORDERS = "/orders";


    @Step("Создание заказа с авторизацией")
    public ValidatableResponse createOrderWithToken(Map<String, String[]> ingredientsMap, String accessToken) {
        return spec()
                .header(EnvConfig.HEADER_AUTHORIZATION, accessToken)
                .body(ingredientsMap)
                .post(ORDERS)
                .then().log().all();
    }

    @Step("Создание заказа без авторизации")
    public ValidatableResponse createOrderWithoutToken(Map<String, String[]> ingredientsMap) {
        return spec()
                .body(ingredientsMap)
                .post(ORDERS)
                .then().log().all();
    }


    @Step("Создание заказа без ингредиентов")
    public ValidatableResponse createOrderWithoutIngredients(String accessToken) {
        return spec()
                .header(EnvConfig.HEADER_AUTHORIZATION, accessToken)
                .post(ORDERS)
                .then().log().all();
    }

    @Step("Получение заказов конкретного пользователя - авторизованный пользователь")
    public ValidatableResponse getOrdersWithToken(String accessToken) {
        return spec()
                .header(EnvConfig.HEADER_AUTHORIZATION, accessToken)
                .get(ORDERS)
                .then().log().all();
    }

    @Step("Получение заказов конкретного пользователя - неавторизованный пользователь")
    public ValidatableResponse getOrdersWithoutToken() {
        return spec()
                .get(ORDERS)
                .then().log().all();
    }
}
