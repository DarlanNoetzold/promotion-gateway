package tech.noetzold;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import io.quarkus.test.security.TestSecurity;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import tech.noetzold.controller.PromotionController;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestHTTPEndpoint(PromotionController.class)
public class PromotionControllerTest {

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
    public void testSavePromotion() {
        given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body("{ \"promoName\": \"Test Promotion\", \"valueType\": \"PERCENT\", \"value\": 20.0, \"promoDescription\": \"Test Description\", \"promotionType\": \"ITEM\", \"promoCompDesc\": \"Test Comp Desc\", \"promoCompType\": 0.5, \"promoDtlId\": 1.0, \"rules\": \"Test Rules\", \"startDate\": \"2023-10-26\", \"endDate\": \"2023-10-27\", \"applyToCode\": \"TESTCODE\", \"discountLimit\": 50.0, \"exceptionParentId\": \"TESTPARENT\" }")
                .when()
                .post("http://localhost:4000/api/promo/v1/promotion")
                .then()
                .statusCode(201);
    }

    @Test
    @Order(2)
    @TestSecurity(user = "admin", roles = {"admin"})
    public void testGetPromotionModelById() {
        given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .when()
                .get("http://localhost:4000/api/promo/v1/promotion/{id}", "8b71d0c5-9f98-4c4a-815c-55e4c0518f6a")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(3)
    @TestSecurity(user = "admin", roles = {"admin"})
    public void testUpdatePromotion() {
        given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body("{ \"promoName\": \"Updated Promotion\", \"promoDescription\": \"Updated Description\" }")
                .when()
                .put("http://localhost:4000/api/promo/v1/promotion/{id}", "8b71d0c5-9f98-4c4a-815c-55e4c0518f6a")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(4)
    @TestSecurity(user = "admin", roles = {"admin"})
    public void testDeletePromotion() {
        given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .when()
                .delete("http://localhost:4000/api/promo/v1/promotion/{id}", "d3e6dbb6-4f9c-4b4d-843e-8f0d8bdc5e58")
                .then()
                .statusCode(200);
    }
}
