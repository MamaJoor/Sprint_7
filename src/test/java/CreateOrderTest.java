import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;


public class CreateOrderTest extends API_methods{

    @Before
    public void setUp(){
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
        createCourier();
    }

    @After
    public void tearDown(){
        deleteCourier();
    }
}
