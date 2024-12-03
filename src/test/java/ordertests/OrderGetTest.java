package ordertests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import java.net.HttpURLConnection;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static steps.UserApiStep.loginUserRequest;
import static steps.UserApiStep.retrieveAndSetAccessToken;

public class OrderGetTest extends OrderBaseTest {
    @Test
    @DisplayName("Авторизованный пользователь получает заказ")
    @Description("Проверка получение заказа авторизованным пользователем")
    public void authUserGetOrder() {
        userApiStep.generateRandomUserData(user);
        userApiStep.createNewUser(user);
        loginUserRequest(user);
        retrieveAndSetAccessToken(user);

        orderApiStep
                .getOrderForAuthorizedUser(user)
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("success", is(true))
                .and()
                .body("total", notNullValue());
    }

    @Test
    @DisplayName("Не авторизованный пользователь получает заказ")
    @Description("Проверка получение заказа не авторизованным пользователем")
    public void notAuthUserGetOrder() {
        orderApiStep
                .getOrderForUnauthorizedUser()
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED)
                .body("success", is(false))
                .and()
                .body("message", is("You should be authorised"));
    }
}
