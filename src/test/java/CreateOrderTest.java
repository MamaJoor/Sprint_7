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

    @Before
    public void setUp(){
        RestAssured.baseURI = SCOOTER_URL;
    }

    @Parameterized.Parameters
    public static Object[][] createOrderParamTestData() {
        return new Object[][]{
                {"Артем", "Беляев", "Пушкина, 12", "Черкизовская", "+7 912 123 45 67", 1, "2023-03-23", "Купи чипсы", null},
                {"Артем", "Беляев", "Пушкина, 12", "Черкизовская", "+7 912 123 45 67", 2, "2023-03-23", "Купи чипсы", List.of("BLACK")},
                {"Артем", "Беляев", "Пушкина, 12", "Черкизовская", "+7 912 123 45 67", 3, "2023-03-23", "Купи чипсы", List.of("GREY")},
                {"Артем", "Беляев", "Пушкина, 12", "Черкизовская", "+7 912 123 45 67", 4, "2023-03-23", "Купи чипсы", List.of("BLACK", "GREY")}
        };
    }

    @DisplayName("Параметризованный тест на создание заказа")
    @Test
    public void createOrderParamTest(){
        API_methods order = new API_methods();
        ValidatableResponse orderResponse  = order.getOrdersResponse(
                new Order(firstNameValue, lastNameValue, addressValue, metroStationValue, phoneValue, rentTimeValue, deliveryDateValue, commentValue, colorValue));
        orderResponse
                .statusCode(201);
        MatcherAssert.assertThat("track", notNullValue());
    }
}