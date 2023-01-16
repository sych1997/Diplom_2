package userstest;

import constants.GenerationUsers;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import stapsapi.CheckUser;
import stapsapi.UserClient;
import users.AuthorizationUsers;
import users.CreatingUsers;

@DisplayName("Проверка авторизации")
public class AuthorizationUsersTest {
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
    @DisplayName("Логин под существующим пользователем")
    public void loginUnderExistingUser() {
        AuthorizationUsers cratedUser = AuthorizationUsers.from(newUser);
        ValidatableResponse response = user.login(cratedUser);
        checkUser.responseOk(response);
    }
    @Test
    @DisplayName("Логин с неверным логином")
    public void logWithInvalidEmail() {
        AuthorizationUsers cratedUser = AuthorizationUsers.from(newUser);
        cratedUser.setEmail("");
        ValidatableResponse response = user.login(cratedUser);
        checkUser.responseUnauthorized(response);
    }
    @Test
    @DisplayName("Логин с неверным паролем")
    public void logWithInvalidPassword() {
        AuthorizationUsers cratedUser = AuthorizationUsers.from(newUser);
        cratedUser.setEmail("");
        ValidatableResponse response = user.login(cratedUser);
        checkUser.responseUnauthorized(response);
    }
}
