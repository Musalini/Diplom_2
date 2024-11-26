package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import model.User;
import org.apache.commons.lang3.RandomStringUtils;

import static constants.UrlConstants.BASE_URI;
import static constants.UrlConstants.USER_BASE_PATH;
import static io.restassured.RestAssured.given;

public class UserApiStep {

    @Step("Генерация случайных данных пользователя")
    public void generateRandomUserData(User user) {
        user.setEmail((RandomStringUtils.randomAlphabetic(8)
                .toLowerCase()) + "@yandex.ru");
        user.setPassword(RandomStringUtils.randomAlphabetic(14));
        user.setName(RandomStringUtils.randomAlphabetic(9));
    }

    @Step("Получение токена авторизации")
    public static void retrieveAndSetAccessToken(User user) {
        String accessToken = loginUserRequest(user).extract()
                .body().path("accessToken");
        user.setAccessToken(accessToken);
    }

    @Step("Создание нового пользователя")
    public ValidatableResponse createNewUser(User user) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(user)
                .when()
                .post(USER_BASE_PATH + "/register")
                .then();
    }


    @Step("Вход пользователя в систему")
    public static ValidatableResponse loginUserRequest(User user) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(user)
                .when()
                .post(USER_BASE_PATH + "/login")
                .then();
    }

    @Step("Обновление данных авторизованного пользователя")
    public static ValidatableResponse updateAuthorizedUserData(User user) {
        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", user.getAccessToken())
                .baseUri(BASE_URI)
                .body(user)
                .when()
                .patch(USER_BASE_PATH + "/user")
                .then();
    }

    @Step("Обновление данных неавторизованного пользователя")
    public static ValidatableResponse updateUnauthorizedUserData(User user) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(user)
                .when()
                .patch(USER_BASE_PATH + "/user")
                .then();
    }

    @Step("Удаление пользователя")
    public void removeUser(User user) {
        given()
                .contentType(ContentType.JSON)
                .header("Authorization", user.getAccessToken())
                .baseUri(BASE_URI)
                .when()
                .delete(USER_BASE_PATH + "/user")
                .then();
    }


//    private User user;
//    private String accessToken;
//
//    private Response sendRequest(String endpoint, String method) {
//        return given().log().all()
//                .contentType(ContentType.JSON)
//                .body(user)
//                .when()
//                .request(method, endpoint);
//    }
//
//    private Response sendRequestWithHeader(String endpoint, String method, String token) {
//        return given().log().all()
//                .contentType(ContentType.JSON)
//                .header("Authorization", token)
//                .when()
//                .request(method, endpoint);
//    }
//
//    @Step("Создание пользователя")
//    public Response createUser() {
//        return sendRequest("register", "POST");
//    }
//
//    @Step("Авторизация пользователя")
//    public Response loginUser() {
//        return sendRequest("login", "POST");
//    }
//
//    @Step("Обновление данных о пользователе")
//    public Response changeUserDataWithAuthorization() {
//        // Получаем токен с помощью нового метода
//        return sendRequestWithHeader("user", "PATCH", accessToken);
//    }
//
//
//    @Step("Обновление данных о пользователе без авторизации")
//    public Response changeUserDataWithoutAuthorization() {
//        return sendRequest("user", "PATCH");
//    }
//
//    @Step("Обновление данных пользователя")
//    public Response patchUser(User user, String accessToken) {
//        return reqSpec
//                .header("Authorization", accessToken)
//                .body(user)
//                .when()
//                .patch("/api/auth" + "/user");
//    }
//
//
//    @Step("Удаление пользователя")
//    public void deleteUser() {
//        String accessToken = loginUser()
//                .then().log().all()
//                .extract().path("accessToken");
//        if (accessToken != null) {
//            sendRequestWithHeader("user", "DELETE", accessToken);
//        }
//    }
}
