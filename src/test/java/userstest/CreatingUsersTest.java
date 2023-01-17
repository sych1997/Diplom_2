package userstest;

import constants.GenerationUsers;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import stapsapi.CheckUser;
import stapsapi.UserClient;

@DisplayName("Проверка создание пользователя")
public class CreatingUsersTest {
    private final GenerationUsers generator = new GenerationUsers();
    private final UserClient user = new UserClient();
    private final CheckUser checkUser = new CheckUser();
    private String accessToken = "null";

    @After
    public void deleteUser() {
        if (!accessToken.equals("null")) {
            ValidatableResponse response = user.delete(accessToken);
            checkUser.responseDeleteOk(response);
        }
    }

    @Test
    @DisplayName("Создание уникального пользователя")
    public void creatingUniqueUser() {
        var newUser = generator.creatingUser();
        ValidatableResponse response = user.create(newUser);
        accessToken = checkUser.responseOk(response);
    }

    @Test
    @DisplayName("Создание не уникального пользователя")
    public void creatingNotUniqueUser() {
        var newUser = generator.creatingUser();
        ValidatableResponse response = user.create(newUser);
        accessToken = checkUser.responseOk(response);
        ValidatableResponse responseTwo = user.create(newUser);
        checkUser.responseForbidden(responseTwo);
    }

    @Test
    @DisplayName("Создание пользователя без эмейла")
    public void сreatingUserWithoutEmail() {
        var newUser = generator.creatingUser();
        newUser.setEmail("");
        ValidatableResponse response = user.create(newUser);
        checkUser.responseForbidden(response);
    }

    @Test
    @DisplayName("Создание пользователя без пароля")
    public void сreatingUserWithoutPassword() {
        var newUser = generator.creatingUser();
        newUser.setPassword("");
        ValidatableResponse response = user.create(newUser);
        checkUser.responseForbidden(response);
    }

    @Test
    @DisplayName("Создание пользователя без имени")
    public void сreatingUserWithoutName() {
        var newUser = generator.creatingUser();
        newUser.setName("");
        ValidatableResponse response = user.create(newUser);
        checkUser.responseForbidden(response);
    }
}
