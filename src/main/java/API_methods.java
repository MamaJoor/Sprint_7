import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class API_methods {

    public void createCourier() {
        Courier courier = new Courier("Artem123", "qwerty123", "Artem");
        given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post("/api/v1/courier");
    }

    public void deleteCourier(){
        Login login = new Login("Artem123", "qwerty123");
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(login)
                .when()
                .post("/api/v1/courier/login");
        String id = response.jsonPath().getString("id");
        given()
                .when()
                .delete("/api/v1/courier/" + id);
    }
    public ValidatableResponse getOrdersResponse(Order order) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .when()
                .post("/api/v1/orders")
                .then();
    }
}
