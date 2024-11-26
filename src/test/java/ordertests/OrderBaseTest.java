package ordertests;

import model.Order;
import model.User;
import org.junit.After;
import org.junit.Before;
import steps.IngredientStep;
import steps.OrderApiStep;
import steps.UserApiStep;

public class OrderBaseTest {
    protected static Order order;
    protected static OrderApiStep orderApiStep;
    protected static UserApiStep userApiStep;
    protected static User user;
    protected static IngredientStep ingredientStep;

    @Before
    public void setUp(){
        order = new Order();
        orderApiStep = new OrderApiStep();
        user = new User();
        userApiStep = new UserApiStep();
        ingredientStep = new IngredientStep();
    }

    @After
    public void tearDown(){
        String userToken = user.getAccessToken();
        if (userToken != null){
            userApiStep.removeUser(user);
        } else {
            System.out.println("Пользователь не найден");
        }
    }
}
