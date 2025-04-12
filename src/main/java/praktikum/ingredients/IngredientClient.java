package praktikum.ingredients;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import praktikum.Client;

import java.util.HashMap;
import java.util.Map;

public class IngredientClient extends Client {

    private static final String INGREDIENTS = "/ingredients";

    @Step("Получение списка ингредиентов")
    public ValidatableResponse getAllIngredients(){
        return spec()
                .get(INGREDIENTS)
                .then().log().all();
    }

    @Step("Добавление ингредиентов в заказ")
    public Map<String, String[]> addIngredientToOrder(String ingredient) {
        String[] ingredientsArray = {ingredient};
        Map<String, String[]> ingredientsMap = new HashMap<>();
        ingredientsMap.put("ingredients", ingredientsArray);
        return ingredientsMap;
    }

    @Step("Добавление двух ингредиентов в заказ")
    public Map<String, String[]> addIngredientsToOrder(String firstIngredient, String secondIngredient) {
        String[] ingredientsArray = {firstIngredient, secondIngredient};
        Map<String, String[]> ingredientsMap = new HashMap<>();
        ingredientsMap.put("ingredients", ingredientsArray);
        return ingredientsMap;
    }

}
