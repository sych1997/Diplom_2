package stapsapi;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CheckUser {

    @Step("Ответ об успешном выполнении запроса")
    public String responseOk(ValidatableResponse response) {
        return response.assertThat()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true))
                .extract()
                .path("accessToken");
    }

    @Step("Ответ об не удачном создании пользователя (Код 403)")
    public void responseForbidden(ValidatableResponse response) {
        response.assertThat()
                .statusCode(HTTP_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", notNullValue());
    }

    @Step("Ответ о том, что не выполнена авторизация (Код 401)")
    public void responseUnauthorized(ValidatableResponse response) {
        response.assertThat()
                .statusCode(HTTP_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", notNullValue());
    }

    @Step("Ответ об успешном удалении пользователя")
    public void responseDeleteOk(ValidatableResponse response) {
        response.assertThat()
                .statusCode(HTTP_ACCEPTED)
                .body("success", equalTo(true));
    }
}
