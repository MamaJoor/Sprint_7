import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.notNullValue;

public class ListOfOrdersTest extends API_methods{

    @Before
    public void setUp(){
        RestAssured.baseURI = SCOOTER_URL;
    }

    @Test
    @DisplayName("Тест на получение списка заказов")
    public void listOfOrdersTest() {
        given()
                .header("Content-type", "application/json")
                .when()
                .get(API_ORDER)
                .then()
                .assertThat()
                .statusCode(200)
                .body("orders", notNullValue());
    }
}
