package ordertests;

import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import steps.UserApiStep;

import java.net.HttpURLConnection;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;

public class OrderCreationTest extends OrderBaseTest {

    @Test
    @DisplayName("Создание заказа с авторизацией и c ингредиентами")
    @Description("Проверка создания заказа авторизованным пользователем с ингредиентами")
    public void createOrderWithAuthUserWithIngredients() {
        userApiStep.generateRandomUserData(user);
        userApiStep.createNewUser(user);
        UserApiStep.loginUserRequest(user);
        UserApiStep.retrieveAndSetAccessToken(user);
        ingredientStep.addIngredientsToOrder(order);

        orderApiStep
                .createOrderForAuthorizedUser(order, user)
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("success", is(true))
                .and()
                .body("name", notNullValue());
    }

    @Test
    @DisplayName("Создание заказа с авторизацией без ингредиентов")
    @Description("Проверка создания заказа авторизованным пользователем без ингредиентов")
    public void createOrderWithAuthUserWithoutIngredients() {
        userApiStep.generateRandomUserData(user);
        userApiStep.createNewUser(user);
        UserApiStep.loginUserRequest(user);
        UserApiStep.retrieveAndSetAccessToken(user);
        ingredientStep.addEmptyIngredientsToOrder(order);

        orderApiStep
                .createOrderForAuthorizedUser(order, user)
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .body("success", is(false))
                .and()
                .body("message", is("Ingredient ids must be provided"));
    }

    @Test
    @Issue("Тест не проходит из-за бага в системе")
    @DisplayName("Создание заказа без авторизации и c ингредиентами")
    @Description("Проверка создания заказа без авторизации пользователя с ингредиентами")
    public void createOrderWithoutAuthUserWithIngredients() {
        ingredientStep.addIngredientsToOrder(order);

        orderApiStep
                .createOrderForUnauthorizedUser(order)
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED)
                .body("success", is(false))
                .and()
                .body("message", is("You should be authorised"));
    }

    @Test
    @Issue("Тест не проходит из-за бага в системе")
    @DisplayName("Создание заказа без авторизации и без ингредиентов")
    @Description("Проверка создания заказа без авторизации пользователя и без ингредиентов")
    public void createOrderWithoutAuthUserWithoutIngredients() {
        ingredientStep.addEmptyIngredientsToOrder(order);

        orderApiStep
                .createOrderForUnauthorizedUser(order)
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED)
                .body("success", is(false))
                .and()
                .body("message", is("You should be authorised"));
    }

    @Test
    @DisplayName("Создание заказа с авторизацией и с неверным хешем ингредиентов")
    @Description("Проверка создания заказа с авторизацией и с неверным хешем ингредиентов")
    public void createOrderWithInvalidHashIngredientAuthUser() {
        userApiStep.generateRandomUserData(user);
        userApiStep.createNewUser(user);
        UserApiStep.loginUserRequest(user);
        UserApiStep.retrieveAndSetAccessToken(user);
        ingredientStep.addInvalidIngredientsToOrder(order);

        orderApiStep
                .createOrderForAuthorizedUser(order, user)
                .statusCode(HttpURLConnection.HTTP_INTERNAL_ERROR);
    }

    @Test
    @Issue("Тест не проходит из-за бага в системе")
    @DisplayName("Создание заказа без авторизации и с неверным хешем ингредиентов")
    @Description("Проверка создания заказа без авторизации и с неверным хешем ингредиентов")
    public void createOrderWithInvalidHashIngredientWithoutAuthUser() {
        ingredientStep.addInvalidIngredientsToOrder(order);

        orderApiStep
                .createOrderForUnauthorizedUser(order)
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED)
                .body("success", is(false))
                .and()
                .body("message", is("You should be authorised"));
    }
}
