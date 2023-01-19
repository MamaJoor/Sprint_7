import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CourierLoginTest extends API_methods {

    @Before
    public void setUp() {
        RestAssured.baseURI = SCOOTER_URL;
        createCourier();
    }

    @After
    public void tearDown(){
        deleteCourier();
    }

    @Test
    @DisplayName("Тест на логин с корректными данными")
    public void loginWithCorrectDataTest() {
        API_methods login = new API_methods();
        ValidatableResponse courier = login.getLoginResponse(
                new Login(LOGIN, PASSWORD));
        courier
                .statusCode(200);
        MatcherAssert.assertThat("id", notNullValue());
    }

    @Test
    @DisplayName("Тест на логин с пустым паролем")
    public void loginWithoutPasswordTest() {
        API_methods login = new API_methods();
        ValidatableResponse courier = login.getLoginResponse(
                new Login(LOGIN, ""));
        courier
                .assertThat()
                .statusCode(400)
                .body("message", CoreMatchers.equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Тест на логин с пустым логином")
    public void loginWithoutLoginTest() {
        API_methods login = new API_methods();
        ValidatableResponse courier = login.getLoginResponse(
                new Login("", PASSWORD));
        courier
                .assertThat()
                .statusCode(400)
                .body("message", CoreMatchers.equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Тест на логин с неправильным паролем")
    public void loginWithIncorrectPasswordTest() {
        API_methods login = new API_methods();
        ValidatableResponse courier = login.getLoginResponse(
                new Login(LOGIN, WRONG_PASSWORD));
        courier
                .assertThat()
                .statusCode(404)
                .body("message", CoreMatchers.equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Тест на логин с неправильным логином")
    public void loginWithIncorrectLoginTest() {
        API_methods login = new API_methods();
        ValidatableResponse courier = login.getLoginResponse(
                new Login(WRONG_LOGIN, PASSWORD));
        courier
                .assertThat()
                .statusCode(404)
                .body("message", CoreMatchers.equalTo("Учетная запись не найдена"));
    }
}
