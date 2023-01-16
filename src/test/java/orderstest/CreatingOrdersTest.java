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

@DisplayName("Проверка создания заказов")
public class CreatingOrdersTest {
    private final GenerationUsers generator = new GenerationUsers();
    private final UserClient user = new UserClient();
    private final OrderClient order = new OrderClient();
    private AllIngredients allIngredients;
    private CreatingOrders orderCreate;
    private List<String> ingredients = new ArrayList<>();
    private final CheckUser checkUser = new CheckUser();
    private final CheckOrder checkOrder = new CheckOrder();
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
    }
    @After
    public void deleteUser() {
        if (!accessToken.equals("null")) {
            ValidatableResponse response = user.delete(accessToken);
            checkUser.responseDeleteOk(response);
        }
    }
    @DisplayName("Создание заказа авторизованным пользователем")
    @Test
    public void creatingOrderWithAuthorization() {
        ValidatableResponse response = order.creatingOrderAuthorizedUser(orderCreate, accessToken);
        checkOrder.successfulCreationOrder(response);
    }
    @DisplayName("Создание заказа не авторизованным пользователем")
    @Test
    public void creatingOrderWithUnauthorization() {
        ValidatableResponse response = order.creatingOrderUnauthorizedUser(orderCreate);
        checkOrder.successfulCreationOrder(response);
    }
    @DisplayName("Создание заказа авторизованным пользователем без ингредиентов")
    @Test
    public void creatingOrderWithAuthorizationWithoutIngredients() {
        CreatingOrders newOrderCreate = new CreatingOrders();
        ValidatableResponse response = order.creatingOrderAuthorizedUser(newOrderCreate, accessToken);
        checkOrder.responseBadRequest(response);
    }
    @DisplayName("Создание заказа авторизованным пользователем с неправильными ингредиентами")
    @Test
    public void creatingOrderWithAuthorizationWithWrongIngredients() {
        orderCreate.setIngredients(List.of("123hjhhg1", "213hg31g123", "123321hjhj"));
        ValidatableResponse response = order.creatingOrderAuthorizedUser(orderCreate, accessToken);
        checkOrder.responseServerError(response);
    }
}
