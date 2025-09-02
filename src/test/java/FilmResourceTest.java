import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

// Testing the API

@QuarkusTest    // By using @QuarkusTest the tests run automatically when rebuilding the application
public class FilmResourceTest {
    @Test
    public void test() {
        given()
                .when().get("/film/5")
                .then()
                .statusCode(200)
                .body(containsString("AFRICAN EGG"));
    }
}
