package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import model.Order;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static constants.UrlConstants.BASE_URI;
import static constants.UrlConstants.INGREDIENTS_BASE_PATH;
import static io.restassured.RestAssured.given;

public class IngredientStep {

    @Step("Выполнить запрос на получение списка ингредиентов")
    public ValidatableResponse fetchIngredientList() {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .when()
                .get(INGREDIENTS_BASE_PATH)
                .then();
    }

    @Step("Добавить в заказ список ингредиентов")
    public void addIngredientsToOrder(Order order) {
        List<String> ingredientId = fetchIngredientList().extract()
                .body().path("data._id");

        List<String> ingredientsList = new ArrayList<>();

        String firstIngredient = ingredientId.get(new Random().nextInt(ingredientId.size()));
        String secondIngredient = ingredientId.get(new Random().nextInt(ingredientId.size()));

        ingredientsList.add(firstIngredient);
        ingredientsList.add(secondIngredient);

        order.setIngredients(ingredientsList);
    }

    @Step("Установить пустой список ингредиентов в заказ")
    public void addEmptyIngredientsToOrder(Order order) {
        List<String> ingredientsList = new ArrayList<>();

        order.setIngredients(ingredientsList);
    }

    @Step("Добавить в заказ некорректный список ингредиентов")
    public void addInvalidIngredientsToOrder(Order order) {
        List<String> ingredientsList = new ArrayList<>();
        ingredientsList.add(RandomStringUtils.randomAlphabetic(3));
        ingredientsList.add(RandomStringUtils.randomAlphabetic(3));
        ingredientsList.add(RandomStringUtils.randomAlphabetic(3));

        order.setIngredients(ingredientsList);
    }
}