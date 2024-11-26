package usertests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.net.HttpURLConnection;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.Matchers.is;
import static steps.UserApiStep.*;

public class UserDataChangeTest extends UserBaseTest {
    private String newEmail;
    private String newPassword;
    private String newName;

    @Before
    public void setUpUser() {
        UserBaseTest.userApiStep.generateRandomUserData(UserBaseTest.user);
        UserBaseTest.userApiStep.createNewUser(UserBaseTest.user);
        loginUserRequest(UserBaseTest.user);
        retrieveAndSetAccessToken(UserBaseTest.user);

        newEmail = randomAlphabetic(20)
                .toLowerCase() + "@yandex.ru";
        newPassword = randomAlphabetic(20);
        newName = randomAlphabetic(20);
    }
    @Test
    @DisplayName("Обновление email пользователя")
    @Description("Проверяет успешное изменение email авторизованного пользователя")
    public void shouldUpdateUserEmail() {
        UserBaseTest.user.setEmail(newEmail);

        updateAuthorizedUserData(UserBaseTest.user)
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("success", is(true))
                .and()
                .body("user.email", Matchers.is(UserBaseTest.user.getEmail()));
    }

    @Test
    @DisplayName("Обновление пароля пользователя")
    @Description("Проверяет успешное изменение пароля авторизованного пользователя")
    public void shouldUpdateUserPassword() {
        UserBaseTest.user.setPassword(newPassword);

        updateAuthorizedUserData(UserBaseTest.user)
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("success", is(true));
    }

    @Test
    @DisplayName("Обновление имени пользователя")
    @Description("Проверяет успешное изменение имени авторизованного пользователя")
    public void shouldUpdateUserName() {
        UserBaseTest.user.setName(newName);

        updateAuthorizedUserData(UserBaseTest.user)
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("success", is(true))
                .and()
                .body("user.name", Matchers.is(UserBaseTest.user.getName()));
    }

    @Test
    @DisplayName("Изменение email без авторизации")
    @Description("Проверяет, что изменение email без авторизации возвращает ошибку 401")
    public void shouldNotUpdateEmailWithoutAuth() {
        UserBaseTest.user.setEmail(newEmail);

        updateUnauthorizedUserData(UserBaseTest.user)
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED)
                .body("success", is(false))
                .and()
                .body("message", is("You should be authorised"));
    }

    @Test
    @DisplayName("Изменение пароля без авторизации")
    @Description("Проверяет, что изменение пароля без авторизации возвращает ошибку 401")
    public void shouldNotUpdatePasswordWithoutAuth() {
        UserBaseTest.user.setPassword(newPassword);

        updateUnauthorizedUserData(UserBaseTest.user)
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED)
                .body("success", is(false))
                .and()
                .body("message", is("You should be authorised"));
    }

    @Test
    @DisplayName("Изменение имени без авторизации")
    @Description("Проверяет, что изменение имени без авторизации возвращает ошибку 401")
    public void shouldNotUpdateNameWithoutAuth() {
        UserBaseTest.user.setName(newName);

        updateUnauthorizedUserData(UserBaseTest.user)
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED)
                .body("success", is(false))
                .and()
                .body("message", is("You should be authorised"));
    }
}