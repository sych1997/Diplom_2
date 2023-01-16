package constants;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class Constants {
    protected static final String BASE_URI = "https://stellarburgers.nomoreparties.site";
    protected static final String API_PREFIX = "/api";

    protected RequestSpecification spec() {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .basePath(API_PREFIX);
    }
}
