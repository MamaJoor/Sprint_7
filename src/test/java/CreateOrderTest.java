import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.List;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest extends API_methods{
    private final String firstNameValue;
    private final String lastNameValue;
    private final String addressValue;
    private final String metroStationValue;
    private final String phoneValue;
    private final int rentTimeValue;
    private final String deliveryDateValue;
    private final String commentValue;
    private final List<String> colorValue;

    public CreateOrderTest(String firstNameValue, String lastNameValue, String addressValue, String metroStationValue, String phoneValue, int rentTimeValue, String deliveryDateValue, String commentValue, List<String> colorValue) {
        this.firstNameValue = firstNameValue;
        this.lastNameValue = lastNameValue;
        this.addressValue = addressValue;
        this.metroStationValue = metroStationValue;
        this.phoneValue = phoneValue;
        this.rentTimeValue = rentTimeValue;
        this.deliveryDateValue = deliveryDateValue;
        this.commentValue = commentValue;
        this.colorValue = colorValue;
    }

    @Parameterized.Parameters
    public static Object[][] getTestDataCreateOrder() {
        return new Object[][]{
                {"Артем", "Беляев", "Пушкина, 12", "Черкизовская", "+7 912 123 45 67", 1, "2023-03-23", "Купи чипсы", null},
                {"Артем", "Беляев", "Пушкина, 12", "Черкизовская", "+7 912 123 45 67", 2, "2023-03-23", "Купи чипсы", List.of("BLACK")},
                {"Артем", "Беляев", "Пушкина, 12", "Черкизовская", "+7 912 123 45 67", 3, "2023-03-23", "Купи чипсы", List.of("GREY")},
                {"Артем", "Беляев", "Пушкина, 12", "Черкизовская", "+7 912 123 45 67", 4, "2023-03-23", "Купи чипсы", List.of("BLACK", "GREY")},
        };
    }

    @Before
    public void setUp(){
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @DisplayName("Параметризованный тест на создание заказа")
    @Test
    public void testTrackFieldInOrder(){
        API_methods order = new API_methods();
        ValidatableResponse emptyPasswordField  = order.getOrdersResponse(
                new Order(firstNameValue, lastNameValue, addressValue, metroStationValue, phoneValue, rentTimeValue, deliveryDateValue, commentValue, colorValue));
        emptyPasswordField
                .statusCode(201);
        MatcherAssert.assertThat("track", notNullValue());
    }
}