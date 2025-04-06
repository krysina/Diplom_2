package praktikum.orders;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import praktikum.EnvConfig;

import java.net.HttpURLConnection;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderChecks {

    @Step("Cоздан заказ с авторизацией")
    public String getIngredientInOrder(ValidatableResponse orderResponse) {
        String orderFirstIngredient = orderResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .and().body(EnvConfig.SUCCESS, equalTo(true))
                .extract()
                .jsonPath().getString("order.ingredients[0]._id");

        return orderFirstIngredient;
    }

    @Step("Получен номер 2-го ингредиента в заказе")
    public String getSecondIngredientInOrder(ValidatableResponse orderResponse) {
        String orderSecondIngredient = orderResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .and().body(EnvConfig.SUCCESS, equalTo(true))
                .extract()
                .jsonPath().getString("order.ingredients[1]._id");

        return orderSecondIngredient;
    }

    @Step("Cоздан заказ без авторизации")
    public String getOrderNumberInOrder(ValidatableResponse orderResponse) {
        String orderNumber = orderResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .and().body(EnvConfig.SUCCESS, equalTo(true))
                .extract()
                .jsonPath().getString("order.number");

        return orderNumber;
    }

    @Step("Не создан заказ без ингредиентов")
    public void notCreatedOrderWithoutIngredients(ValidatableResponse orderResponse) {
        orderResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .and().body(EnvConfig.SUCCESS, equalTo(false))
                .and().body(EnvConfig.MESSAGE, equalTo(EnvConfig.TEXT_MESSAGE_NO_INGREDIENTS));
    }

    @Step("Не создан заказ c неверным хешем ингредиентов")
    public void notCreatedOrderWithInvalidHashIngredients(ValidatableResponse orderResponse) {
        orderResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_INTERNAL_ERROR);
    }

    @Step("Получен заказ авторизованного пользователя")
    public void getOrderWithToken(ValidatableResponse getOrderResponse) {
        getOrderResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .and().body(EnvConfig.SUCCESS, equalTo(true))
                .and().body("orders", notNullValue());
    }

    @Step("Не получен заказ неавторизованного пользователя")
    public void notGottenOrderWithoutToken(ValidatableResponse getOrderResponse) {
        getOrderResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED)
                .and().body(EnvConfig.SUCCESS, equalTo(false))
                .and().body(EnvConfig.MESSAGE, equalTo(EnvConfig.TEXT_MESSAGE_NOT_AUTHORISED));
    }
}
