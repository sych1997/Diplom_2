package stapsapi;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CheckOrder {

    @Step("Ответ об успешном создании пользователя")
    public void successfulCreationOrder(ValidatableResponse response) {
        response.assertThat()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true))
                .body("order.number", notNullValue());
    }

    @Step("Ответ о неправильном составлении запроса")
    public void responseBadRequest(ValidatableResponse response) {
        response.assertThat()
                .statusCode(HTTP_BAD_REQUEST)
                .body("success", equalTo(false))
                .body("message", notNullValue());
    }

    @Step("Ответ о том, что сервер не может обработать запрос")
    public void responseServerError(ValidatableResponse response) {
        response.assertThat()
                .statusCode(HTTP_SERVER_ERROR);
    }

    @Step("Ответ о том, что не выполнена авторизация (Код 401)")
    public void responseUnauthorized(ValidatableResponse response) {
        response.assertThat()
                .statusCode(HTTP_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", notNullValue());
    }

    @Step("Ответ об успешном получении списка заказов")
    public void successfulReceiptListOrder(ValidatableResponse response) {
        response.assertThat()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true))
                .body("orders", notNullValue());
    }
}
