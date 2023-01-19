import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class CreateCourierTest extends API_methods{

    @Before
    public void setUp(){
        RestAssured.baseURI = SCOOTER_URL;
    }

    @After
    public void tearDown(){
        deleteCourier();
    }

    @Test
    @DisplayName("Тест на создание курьера с корректными данными")
    public void createNewCourierTest() {
        API_methods createCourier = new API_methods();
        ValidatableResponse courier  = createCourier.getCourierResponse(
                new Courier(LOGIN, PASSWORD, FIRST_NAME));
        courier
                .assertThat()
                .statusCode(201)
                .body("ok", is(true));
    }

    @Test
    @DisplayName("Тест на создание 2х одинаковых курьеров ")
    public void createSameCourierTest() {
        API_methods createCourier = new API_methods();
        ValidatableResponse courier  = createCourier.getCourierResponse(
                new Courier(LOGIN, PASSWORD, FIRST_NAME));
        ValidatableResponse sameCourier  = createCourier.getCourierResponse(
                new Courier(LOGIN, PASSWORD, FIRST_NAME));
        sameCourier
                .assertThat()
                .statusCode(409)
                .body("message", CoreMatchers.equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Тест на создание курьера без пароля")
    public void createCourierWithoutPasswordTest() {
        API_methods createCourier = new API_methods();
        ValidatableResponse courier  = createCourier.getCourierResponse(
                new Courier(LOGIN, "", FIRST_NAME));
        courier
                .assertThat()
                .statusCode(400)
                .body("message", CoreMatchers.equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Тест на создание курьера без логина")
    public void createCourierWithoutLoginTest() {
        API_methods createCourier = new API_methods();
        ValidatableResponse courier  = createCourier.getCourierResponse(
                new Courier("", PASSWORD, FIRST_NAME));
        courier
                .assertThat()
                .statusCode(400)
                .body("message", CoreMatchers.equalTo("Недостаточно данных для создания учетной записи"));
    }
}
