package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import model.Order;
import model.User;

import static constants.UrlConstants.BASE_URI;
import static constants.UrlConstants.ORDER_BASE_PATH;
import static io.restassured.RestAssured.given;

public class OrderApiStep {
    @Step("Отправка запроса на создание заказа для авторизованного пользователя")
    public ValidatableResponse createOrderForAuthorizedUser(Order order, User user) {
        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", user.getAccessToken())
                .baseUri(BASE_URI)
                .body(order)
                .when()
                .post(ORDER_BASE_PATH)
                .then();
    }

    @Step("Отправка запроса на создание заказа для пользователя без авторизации")
    public ValidatableResponse createOrderForUnauthorizedUser(Order order) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(order)
                .when()
                .post(ORDER_BASE_PATH)
                .then();
    }

    @Step("Отправка запроса на получение заказа для авторизованного пользователя")
    public ValidatableResponse getOrderForAuthorizedUser(User user) {
        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", user.getAccessToken())
                .baseUri(BASE_URI)
                .when()
                .get(ORDER_BASE_PATH)
                .then();
    }

    @Step("Отправка запроса на получение заказа для пользователя без авторизации")
    public ValidatableResponse getOrderForUnauthorizedUser() {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .when()
                .get(ORDER_BASE_PATH)
                .then();
    }
}