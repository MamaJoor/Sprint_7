import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CourierLoginTest extends API_methods {

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
        createCourier();
    }

    @After
    public void tearDown(){
        deleteCourier();
    }

    @Test
    @DisplayName("Тест на логин с корректными данными")
    public void loginWithCorrectDataTest() {
        Login login = new Login("Artem123", "qwerty123");
        given()
                .header("Content-type", "application/json")
                .body(login)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .assertThat()
                .statusCode(200)
                .body("id",notNullValue());
    }

    @Test
    @DisplayName("Тест на логин с пустым паролем")
    public void loginWithoutPasswordTest() {
        Login login = new Login("Artem123", "");
        given()
                .header("Content-type", "application/json")
                .body(login)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .assertThat()
                .statusCode(400)
                .body("message", CoreMatchers.equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Тест на логин с пустым логином")
    public void loginWithoutLoginTest() {
        Login login = new Login("", "qwerty123");
        given()
                .header("Content-type", "application/json")
                .body(login)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .assertThat()
                .statusCode(400)
                .body("message", CoreMatchers.equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Тест на логин с неправильным паролем")
    public void loginWithIncorrectPasswordTest() {
        Login login = new Login("Artem123", "qwerty456");
        given()
                .header("Content-type", "application/json")
                .body(login)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .assertThat()
                .statusCode(404)
                .body("message", CoreMatchers.equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Тест на логин с неправильным логином")
    public void loginWithIncorrectLoginTest() {
        Login login = new Login("Artem456", "qwerty123");
        given()
                .header("Content-type", "application/json")
                .body(login)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .assertThat()
                .statusCode(404)
                .body("message", CoreMatchers.equalTo("Учетная запись не найдена"));
    }
}
