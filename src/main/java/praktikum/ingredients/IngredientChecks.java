package praktikum.ingredients;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import praktikum.EnvConfig;

import java.net.HttpURLConnection;

import static org.hamcrest.CoreMatchers.equalTo;

public class IngredientChecks {

    @Step("Получен первый ингредиент")
    public String getFirstIngredient(ValidatableResponse getResponse) {
        String firstIngredient = getResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .and().body(EnvConfig.SUCCESS, equalTo(true))
                .extract()
                .jsonPath().getString("data[0]._id");

        return firstIngredient;
    }

    @Step("Получен второй ингредиент")
    public String getSecondIngredient(ValidatableResponse getResponse) {
        String secondIngredient = getResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .and().body(EnvConfig.SUCCESS, equalTo(true))
                .extract()
                .jsonPath().getString("data[1]._id");

        return secondIngredient;
    }
}
