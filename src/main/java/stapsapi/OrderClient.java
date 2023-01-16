package stapsapi;

import constants.Constants;
import ingredients.AllIngredients;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import orders.CreatingOrders;

public class OrderClient extends Constants {
    protected final String ROOT = "/orders";

    @Step("Получение списка всех ингредиентов")
    public AllIngredients getAllIngredients() {
        return spec()
                .when()
                .get("/ingredients")
                .body().as(AllIngredients.class);
    }
    @Step("Создание заказа авторизованным пользователем")
    public ValidatableResponse creatingOrderAuthorizedUser(CreatingOrders creatingOrders, String accessToken) {
        return spec()
                .header("Authorization", accessToken)
                .body(creatingOrders)
                .when()
                .post(ROOT)
                .then().log().all();
    }
    @Step("Создание заказа не авторизованным пользователем")
    public ValidatableResponse creatingOrderUnauthorizedUser(CreatingOrders creatingOrders) {
        return spec()
                .body(creatingOrders)
                .when()
                .post(ROOT)
                .then().log().all();
    }
    @Step("Получение заказа авторизованным пользователем")
    public ValidatableResponse getOrdersWithAuthorization(String accessToken) {
        return spec()
                .header("Authorization", accessToken)
                .when()
                .get(ROOT)
                .then().log().all();
    }
    @Step("Получение заказа авторизованным пользователем")
    public ValidatableResponse getOrdersWithoutAuthorization(String accessToken) {
        return spec()
                .when()
                .get(ROOT)
                .then().log().all();
    }
}
