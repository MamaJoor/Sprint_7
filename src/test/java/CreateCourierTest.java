import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class CreateCourierTest extends API_methods{

    @Before
    public void setUp(){
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @After
    public void tearDown(){
        deleteCourier();
    }

    @Test
    @DisplayName("Тест на создание курьера с корректными данными")
    public void createNewCourierTest() {
        Courier courier = new Courier("Artem123", "qwerty123", "Artem");
        given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then().assertThat()
                .statusCode(201)
                .body("ok", is(true));
    }

    @Test
    @DisplayName("Тест на создание 2х одинаковых курьеров ")
    public void createSameCourierTest() {
        Courier courier = new Courier("Artem123", "qwerty123", "Artem");
        given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post("/api/v1/courier");
        given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then().assertThat()
                .statusCode(409)
                .body("message", CoreMatchers.equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Тест на создание курьера без пароля")
    public void createCourierWithoutPasswordTest() {
        Courier courier = new Courier("Artem123", "", "Artem");
        given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then().assertThat()
                .statusCode(400)
                .body("message", CoreMatchers.equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Тест на создание курьера без логина")
    public void createCourierWithoutLoginTest() {
        Courier courier = new Courier("", "qwerty123", "Artem");
        given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then().assertThat()
                .statusCode(400)
                .body("message", CoreMatchers.equalTo("Недостаточно данных для создания учетной записи"));
    }
}
