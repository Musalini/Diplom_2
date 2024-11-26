package usertests;

import model.User;
import org.junit.After;
import org.junit.Before;
import steps.UserApiStep;

public class UserBaseTest {
    protected static UserApiStep userApiStep;
    protected static User user;


    @Before
    public void setUp() {
        user = new User();
        userApiStep = new UserApiStep();
    }

    @After
    public void tearDown() {
        String userToken = user.getAccessToken();
        if (userToken != null) {
            userApiStep.removeUser(user);
        } else {
            System.out.println("Пользователь не авторизован");
        }
    }
}
