package usertests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import steps.UserApiStep;

import java.net.HttpURLConnection;

import static org.hamcrest.Matchers.is;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class UserLoginBaseTest extends UserBaseTest {
    @Before
    public void prepareUserForAuth() {
        UserBaseTest.userApiStep.generateRandomUserData(UserBaseTest.user);
        UserBaseTest.userApiStep.createNewUser(UserBaseTest.user);
    }

    @Test
    @DisplayName("Успешная авторизация пользователя")
    @Description("Проверка, что пользователь может успешно авторизоваться с корректными данными")
    public void shouldAuthorizeUserSuccessfully() {
        UserApiStep
                .loginUserRequest(UserBaseTest.user)
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("success", is(true));
    }

    @Test
    @DisplayName("Авторизация с некорректным email")
    @Description("Проверка, что авторизация с неверным email возвращает ошибку 401")
    public void shouldNotAuthorizeWithInvalidEmail() {
        UserBaseTest.user.setEmail(randomAlphabetic(20).toLowerCase() + "@yandex.ru");

        UserApiStep
                .loginUserRequest(UserBaseTest.user)
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED)
                .body("success", is(false))
                .and()
                .body("message", is("email or password are incorrect"));
    }

    @Test
    @DisplayName("Авторизация с некорректным паролем")
    @Description("Проверка, что авторизация с неверным паролем возвращает ошибку 401")
    public void shouldNotAuthorizeWithInvalidPassword() {
        UserBaseTest.user.setPassword(randomAlphabetic(18));

        UserApiStep
                .loginUserRequest(UserBaseTest.user)
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED)
                .body("success", is(false))
                .and()
                .body("message", is("email or password are incorrect"));
    }
}