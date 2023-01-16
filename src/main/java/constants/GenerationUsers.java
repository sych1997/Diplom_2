package constants;

import com.github.javafaker.Faker;
import users.CreatingUsers;

public class GenerationUsers {
    Faker faker = new Faker();

    public CreatingUsers creatingUser() {
        return new CreatingUsers(faker.internet().safeEmailAddress(), faker.internet().password(), faker.name().name());
    }
}
