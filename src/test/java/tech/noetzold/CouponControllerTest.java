package tech.noetzold;



import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import io.quarkus.test.security.TestSecurity;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import tech.noetzold.controller.CouponController;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestHTTPEndpoint(CouponController.class)
public class CouponControllerTest {

    private String accessToken;

    @BeforeEach
    public void obtainAccessToken() {
        final String username = "admin";
        final String password = "admin";

        final String tokenEndpoint = "http://localhost:8180/realms/quarkus1/protocol/openid-connect/token";

        final Map<String, String> requestData = new HashMap<>();
        requestData.put("username", username);
        requestData.put("password", password);
        requestData.put("grant_type", "password");

        final Response response = given()
                .auth().preemptive().basic("backend-service", "secret")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .formParams(requestData)
                .when()
                .post(tokenEndpoint);

        response.then().statusCode(200);

        this.accessToken = response.jsonPath().getString("access_token");
    }


    @Test
    @Order(1)
    @TestSecurity(user = "admin", roles = {"admin"})
    public void testSaveCoupon() {
        given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body("{ \"promotion\": { \"promoId\": \"8b71d0c5-9f98-4c4a-815c-55e4c0518f6a\" }, \"code\": \"COUPON123\" }")
                .when()
                .post("http://localhost:5000/api/promo/v1/coupon")
                .then()
                .statusCode(201);
    }

    @Test
    @Order(2)
    @TestSecurity(user = "admin", roles = {"admin"})
    public void testGetCouponModelById() {
        given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .when()
                .get("http://localhost:5000/api/promo/v1/coupon/{id}", "26c0dd7d-2c63-4a5e-9b87-94d7b5cb9c98")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(3)
    @TestSecurity(user = "admin", roles = {"admin"})
    public void testUpdateCoupon() {
        given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body("{ \"promotion\": { \"promoId\": \"8b71d0c5-9f98-4c4a-815c-55e4c0518f6a\" }, \"code\": \"UPDATED_COUPON\" }") // Substitua "your_promo_id" pelo ID da promoção existente
                .when()
                .put("http://localhost:5000/api/promo/v1/coupon/{id}", "26c0dd7d-2c63-4a5e-9b87-94d7b5cb9c98")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(4)
    @TestSecurity(user = "admin", roles = {"admin"})
    public void testDeleteCoupon() {
        given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .when()
                .delete("http://localhost:5000/api/promo/v1/coupon/{id}", "b3ac9a42-708e-47bb-8f41-299d438cb9f7")
                .then()
                .statusCode(200);
    }


}
