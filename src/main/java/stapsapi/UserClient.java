package stapsapi;

import constants.Constants;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import users.AuthorizationUsers;
import users.CreatingUsers;

public class UserClient extends Constants {
    protected final String ROOT = "/auth";

    @Step("Создание пользователя")
    public ValidatableResponse create(CreatingUsers user) {
        return spec()
                .body(user)
                .when()
                .post(ROOT + "/register")
                .then().log().all();
    }
    @Step("Удаление пользователя")
    public ValidatableResponse delete(String accessToken) {
        return spec()
                .header("Authorization", accessToken)
                .when()
                .delete(ROOT + "/user")
                .then().log().all();
    }
    @Step("Авторизация пользователя")
    public ValidatableResponse login(AuthorizationUsers login) {
        return spec()
                .body(login)
                .when()
                .post(ROOT + "/login")
                .then().log().all();
    }
    @Step("Изменение данных авторизованного пользователя")
    public ValidatableResponse changeDataAuthorizedUser(CreatingUsers user, String accessToken) {
        return spec()
                .header("Authorization", accessToken)
                .body(user)
                .when()
                .patch(ROOT + "/user")
                .then().log().all();
    }
    @Step("Изменение данных не авторизованного пользователя")
    public ValidatableResponse changeDataUnauthorizedUser(CreatingUsers user) {
        return spec()
                .body(user)
                .when()
                .patch(ROOT + "/user")
                .then().log().all();
    }
}
