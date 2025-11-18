package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.containsString;

@QuarkusTest
@DisplayName("REST Demo API Tests")
public class RESTDemoTest {

    // ========== GET /api/restdemo ==========
    
    @Test
    @DisplayName("GET /api/restdemo should return demo message")
    public void testDemoEndpoint() {
        given()
            .when()
                .get("/api/restdemo")
            .then()
                .statusCode(200)
                .contentType(ContentType.TEXT)
                .body(is("This is a REST endpoint in Quarkus"));
    }

    // ========== GET /api/restdemo/{id} ==========
    
    @Test
    @DisplayName("GET /api/restdemo/{id} with valid ID should return success")
    public void testDemoWithValidPathParam() {
        given()
            .pathParam("id", 123)
            .when()
                .get("/api/restdemo/{id}")
            .then()
                .statusCode(200)
                .contentType(ContentType.TEXT)
                .body(containsString("id: 123"));
    }

    @Test
    @DisplayName("GET /api/restdemo/{id} with zero should return 400")
    public void testDemoWithZeroId() {
        given()
            .pathParam("id", 0)
            .when()
                .get("/api/restdemo/{id}")
            .then()
                .statusCode(400);
    }

    @Test
    @DisplayName("GET /api/restdemo/{id} with negative ID should return 400")
    public void testDemoWithNegativeId() {
        given()
            .pathParam("id", -5)
            .when()
                .get("/api/restdemo/{id}")
            .then()
                .statusCode(400);
    }

    // ========== POST /api/restdemo ==========
    
    @Test
    @DisplayName("POST /api/restdemo with valid user should return 201")
    public void testCreateUserWithValidData() {
        given()
            .contentType(ContentType.JSON)
            .body("{\"name\":\"Mario Rossi\",\"age\":30}")
            .when()
                .post("/api/restdemo")
            .then()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .body("name", is("Mario Rossi"))
                .body("age", is(30));
    }

    @Test
    @DisplayName("POST /api/restdemo with empty name should return 400")
    public void testCreateUserWithEmptyName() {
        given()
            .contentType(ContentType.JSON)
            .body("{\"name\":\"\",\"age\":30}")
            .when()
                .post("/api/restdemo")
            .then()
                .statusCode(400);
    }

    @Test
    @DisplayName("POST /api/restdemo with null name should return 400")
    public void testCreateUserWithNullName() {
        given()
            .contentType(ContentType.JSON)
            .body("{\"age\":30}")
            .when()
                .post("/api/restdemo")
            .then()
                .statusCode(400);
    }

    @Test
    @DisplayName("POST /api/restdemo with short name should return 400")
    public void testCreateUserWithShortName() {
        given()
            .contentType(ContentType.JSON)
            .body("{\"name\":\"A\",\"age\":30}")
            .when()
                .post("/api/restdemo")
            .then()
                .statusCode(400);
    }

    @Test
    @DisplayName("POST /api/restdemo with too long name should return 400")
    public void testCreateUserWithTooLongName() {
        String longName = "A".repeat(101);
        given()
            .contentType(ContentType.JSON)
            .body("{\"name\":\"" + longName + "\",\"age\":30}")
            .when()
                .post("/api/restdemo")
            .then()
                .statusCode(400);
    }

    @Test
    @DisplayName("POST /api/restdemo with null age should return 400")
    public void testCreateUserWithNullAge() {
        given()
            .contentType(ContentType.JSON)
            .body("{\"name\":\"Mario Rossi\"}")
            .when()
                .post("/api/restdemo")
            .then()
                .statusCode(400);
    }

    @Test
    @DisplayName("POST /api/restdemo with negative age should return 400")
    public void testCreateUserWithNegativeAge() {
        given()
            .contentType(ContentType.JSON)
            .body("{\"name\":\"Mario Rossi\",\"age\":-5}")
            .when()
                .post("/api/restdemo")
            .then()
                .statusCode(400);
    }

    @Test
    @DisplayName("POST /api/restdemo with unrealistic age should return 400")
    public void testCreateUserWithUnrealisticAge() {
        given()
            .contentType(ContentType.JSON)
            .body("{\"name\":\"Mario Rossi\",\"age\":999}")
            .when()
                .post("/api/restdemo")
            .then()
                .statusCode(400);
    }

    @Test
    @DisplayName("POST /api/restdemo with age 0 should return 201")
    public void testCreateUserWithZeroAge() {
        given()
            .contentType(ContentType.JSON)
            .body("{\"name\":\"Neonato\",\"age\":0}")
            .when()
                .post("/api/restdemo")
            .then()
                .statusCode(201)
                .body("age", is(0));
    }

    @Test
    @DisplayName("POST /api/restdemo with age 150 should return 201")
    public void testCreateUserWithMaxAge() {
        given()
            .contentType(ContentType.JSON)
            .body("{\"name\":\"Matusalemme\",\"age\":150}")
            .when()
                .post("/api/restdemo")
            .then()
                .statusCode(201)
                .body("age", is(150));
    }

    @Test
    @DisplayName("POST /api/restdemo without Content-Type should return 415")
    public void testCreateUserWithoutContentType() {
        given()
            .body("{\"name\":\"Mario Rossi\",\"age\":30}")
            .when()
                .post("/api/restdemo")
            .then()
                .statusCode(415);
    }

    @Test
    @DisplayName("POST /api/restdemo with invalid JSON should return 400")
    public void testCreateUserWithInvalidJson() {
        given()
            .contentType(ContentType.JSON)
            .body("{invalid json}")
            .when()
                .post("/api/restdemo")
            .then()
                .statusCode(400);
    }
}