import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class API_methods extends Constants {

    public void createCourier() {
        Courier courier = new Courier(LOGIN, PASSWORD, FIRST_NAME);
        given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(API_COURIER);
    }

    public void deleteCourier(){
        Login login = new Login(LOGIN, PASSWORD);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(login)
                        .when()
                        .post(API_LOGIN);
        String id = response.jsonPath().getString("id");
        given()
                .when()
                .delete(API_COURIER + id);
    }

    public ValidatableResponse getCourierResponse(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(API_COURIER)
                .then();
    }

    public ValidatableResponse getLoginResponse(Login login) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(login)
                .when()
                .post(API_LOGIN)
                .then();
    }

    public ValidatableResponse getOrdersResponse(Order order) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .when()
                .post(API_ORDER)
                .then();
    }
}
