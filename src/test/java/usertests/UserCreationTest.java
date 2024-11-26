package usertests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;

import java.net.HttpURLConnection;

import static org.hamcrest.CoreMatchers.is;

public class UserCreationTest extends UserBaseTest {

    @Before
    public void setUpUser() {
        UserBaseTest.userApiStep.generateRandomUserData(UserBaseTest.user);
    }

    @Test
    @DisplayName("Создание нового пользователя")
    @Description("Тест проверяет успешное создание пользователя с корректными данными")
    public void shouldCreateUserSuccessfully() {
        UserBaseTest.userApiStep
                .createNewUser(UserBaseTest.user)
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("success", is(true));
    }

    @Test
    @DisplayName("Создание пользователя с уже зарегистрированным email")
    @Description("Тест проверяет, что невозможно создать пользователя с уже существующим email, ответ 403")
    public void shouldNotAllowDuplicateUserCreation() {
        UserBaseTest.userApiStep.createNewUser(UserBaseTest.user);

        UserBaseTest.userApiStep
                .createNewUser(UserBaseTest.user)
                .statusCode(HttpURLConnection.HTTP_FORBIDDEN)
                .body("success", is(false))
                .and()
                .body("message", is("User already exists"));
    }

    @Test
    @DisplayName("Создание пользователя с пустым email")
    @Description("Тест проверяет, что создание пользователя с пустым email возвращает ошибку 403")
    public void shouldNotCreateUserWithEmptyEmail() {
        UserBaseTest.user.setEmail("");

        UserBaseTest.userApiStep
                .createNewUser(UserBaseTest.user)
                .statusCode(HttpURLConnection.HTTP_FORBIDDEN)
                .body("success", is(false));
    }

    @Test
    @DisplayName("Создание пользователя с пустым паролем")
    @Description("Тест проверяет, что создание пользователя с пустым паролем возвращает ошибку 403")
    public void shouldNotCreateUserWithEmptyPassword() {
        UserBaseTest.user.setPassword("");

        UserBaseTest.userApiStep
                .createNewUser(UserBaseTest.user)
                .statusCode(HttpURLConnection.HTTP_FORBIDDEN)
                .body("success", is(false));
    }
}
