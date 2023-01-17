package orderstest;

import constants.GenerationUsers;
import ingredients.AllIngredients;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import orders.CreatingOrders;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import stapsapi.CheckOrder;
import stapsapi.CheckUser;
import stapsapi.OrderClient;
import stapsapi.UserClient;
import users.CreatingUsers;

import java.util.ArrayList;
import java.util.List;

@DisplayName("Проверка получения списка заказов")
public class GetOrdersTest {
    private final GenerationUsers generator = new GenerationUsers();
    private final UserClient user = new UserClient();
    private final OrderClient order = new OrderClient();
    private final CheckUser checkUser = new CheckUser();
    private final CheckOrder checkOrder = new CheckOrder();
    private AllIngredients allIngredients;
    private CreatingOrders orderCreate;
    private final List<String> ingredients = new ArrayList<>();
    private String accessToken = "null";
    private CreatingUsers newUser;

    @Before
    public void createOrder() {
        newUser = generator.creatingUser();
        ValidatableResponse response = user.create(newUser);
        accessToken = checkUser.responseOk(response);
        allIngredients = order.getAllIngredients();
        ingredients.add(allIngredients.getData().get(0).get_id());
        ingredients.add(allIngredients.getData().get(2).get_id());
        ingredients.add(allIngredients.getData().get(3).get_id());
        orderCreate = new CreatingOrders(ingredients);
        order.creatingOrderAuthorizedUser(orderCreate, accessToken);
    }

    @After
    public void deleteUser() {
        if (!accessToken.equals("null")) {
            ValidatableResponse response = user.delete(accessToken);
            checkUser.responseDeleteOk(response);
        }
    }

    @DisplayName("Проверка получения списка заказов авторизованным пользователем")
    @Test
    public void getOrdersByAuthorizedUser() {
        ValidatableResponse response = order.getOrdersWithAuthorization(accessToken);
        checkOrder.successfulReceiptListOrder(response);
    }

    @DisplayName("Проверка получения списка заказов не авторизованным пользователем")
    @Test
    public void getOrdersByUnauthorizedUser() {
        ValidatableResponse response = order.getOrdersWithoutAuthorization(accessToken);
        checkOrder.responseUnauthorized(response);
    }
}
