package userstest;

import constants.GenerationUsers;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import stapsapi.CheckUser;
import stapsapi.UserClient;
import users.CreatingUsers;

@DisplayName("Проверка изменения данных пользователя")
public class ChangingUserDataTest {
    private final GenerationUsers generator = new GenerationUsers();
    private final UserClient user = new UserClient();
    private final CheckUser checkUser = new CheckUser();
    private String accessToken = "null";
    private CreatingUsers newUser;

    @Before
    public void createUser() {
        newUser = generator.creatingUser();
        ValidatableResponse response = user.create(newUser);
        accessToken = checkUser.responseOk(response);
    }
    @After
    public void deleteUser() {
        if (!accessToken.equals("null")) {
            ValidatableResponse response = user.delete(accessToken);
            checkUser.responseDeleteOk(response);
        }
    }
    @Test
    @DisplayName("Проверка изменения данных у авторизованного пользователя")
    public void changingDataAuthorizedUser() {
        ValidatableResponse response = user.changeDataAuthorizedUser(newUser, accessToken);
        checkUser.responseOk(response);
    }
    @Test
    @DisplayName("Проверка изменения данных у не авторизованного пользователя")
    public void changingDataUnauthorizedUser() {
        ValidatableResponse response = user.changeDataUnauthorizedUser(newUser);
        checkUser.responseUnauthorized(response);
    }
}
