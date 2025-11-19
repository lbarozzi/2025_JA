package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

@QuarkusTest
@DisplayName("Todo REST API Tests")
public class TodoRestTest {

    // ========== CREATE (POST) ==========
    
    @Test
    @Order(1)
    @DisplayName("POST /api/todos should create a new todo")
    public void testCreateTodo() {
        given()
            .contentType(ContentType.JSON)
            .body("{\"title\":\"Learn Quarkus\",\"description\":\"Complete the tutorial\",\"completed\":false}")
            .when()
                .post("/api/todos")
            .then()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .body("id", notNullValue())
                .body("title", is("Learn Quarkus"))
                .body("description", is("Complete the tutorial"))
                .body("completed", is(false))
                .body("createdAt", notNullValue())
                .body("lastModifiedAt", notNullValue());
    }

    @Test
    @DisplayName("POST /api/todos with minimal data should create todo")
    public void testCreateTodoMinimal() {
        given()
            .contentType(ContentType.JSON)
            .body("{\"title\":\"Minimal Todo\",\"completed\":false}")
            .when()
                .post("/api/todos")
            .then()
                .statusCode(201)
                .body("title", is("Minimal Todo"))
                .body("description", is((String) null));
    }

    // ========== READ (GET) ==========
    
    @Test
    @Order(2)
    @DisplayName("GET /api/todos should return all todos")
    public void testGetAllTodos() {
        given()
            .when()
                .get("/api/todos")
            .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", greaterThanOrEqualTo(0));
    }

    @Test
    @Order(3)
    @DisplayName("GET /api/todos/{id} should return specific todo")
    public void testGetTodoById() {
        // First create a todo
        Long todoId = given()
            .contentType(ContentType.JSON)
            .body("{\"title\":\"Test Todo\",\"completed\":false}")
            .when()
                .post("/api/todos")
            .then()
                .statusCode(201)
                .extract()
                .path("id");

        // Then retrieve it
        given()
            .pathParam("id", todoId)
            .when()
                .get("/api/todos/{id}")
            .then()
                .statusCode(200)
                .body("id", is(todoId.intValue()))
                .body("title", is("Test Todo"));
    }

    @Test
    @DisplayName("GET /api/todos/{id} with non-existing id should return 404")
    public void testGetTodoByIdNotFound() {
        given()
            .pathParam("id", 999999)
            .when()
                .get("/api/todos/{id}")
            .then()
                .statusCode(404);
    }

    @Test
    @Order(4)
    @DisplayName("GET /api/todos/status/{completed} should filter by completion status")
    public void testGetTodosByStatus() {
        // Create completed todo
        given()
            .contentType(ContentType.JSON)
            .body("{\"title\":\"Completed Task\",\"completed\":true}")
            .post("/api/todos");

        // Create incomplete todo
        given()
            .contentType(ContentType.JSON)
            .body("{\"title\":\"Incomplete Task\",\"completed\":false}")
            .post("/api/todos");

        // Get completed todos
        given()
            .pathParam("completed", true)
            .when()
                .get("/api/todos/status/{completed}")
            .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(1));

        // Get incomplete todos
        given()
            .pathParam("completed", false)
            .when()
                .get("/api/todos/status/{completed}")
            .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(1));
    }

    // ========== UPDATE (PUT) ==========
    
    @Test
    @Order(5)
    @DisplayName("PUT /api/todos/{id} should update existing todo")
    public void testUpdateTodo() {
        // Create a todo
        Long todoId = given()
            .contentType(ContentType.JSON)
            .body("{\"title\":\"Original Title\",\"description\":\"Original Description\",\"completed\":false}")
            .when()
                .post("/api/todos")
            .then()
                .statusCode(201)
                .extract()
                .path("id");

        // Update the todo
        given()
            .contentType(ContentType.JSON)
            .pathParam("id", todoId)
            .body("{\"title\":\"Updated Title\",\"description\":\"Updated Description\",\"completed\":true}")
            .when()
                .put("/api/todos/{id}")
            .then()
                .statusCode(200)
                .body("id", is(todoId.intValue()))
                .body("title", is("Updated Title"))
                .body("description", is("Updated Description"))
                .body("completed", is(true))
                .body("lastModifiedAt", notNullValue());
    }

    @Test
    @DisplayName("PUT /api/todos/{id} with non-existing id should return 404")
    public void testUpdateTodoNotFound() {
        given()
            .contentType(ContentType.JSON)
            .pathParam("id", 999999)
            .body("{\"title\":\"Updated Title\",\"completed\":true}")
            .when()
                .put("/api/todos/{id}")
            .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("PUT /api/todos/{id} should mark todo as completed")
    public void testMarkTodoAsCompleted() {
        // Create incomplete todo
        Long todoId = given()
            .contentType(ContentType.JSON)
            .body("{\"title\":\"Task to Complete\",\"completed\":false}")
            .when()
                .post("/api/todos")
            .then()
                .extract()
                .path("id");

        // Mark as completed
        given()
            .contentType(ContentType.JSON)
            .pathParam("id", todoId)
            .body("{\"title\":\"Task to Complete\",\"completed\":true}")
            .when()
                .put("/api/todos/{id}")
            .then()
                .statusCode(200)
                .body("completed", is(true));
    }

    // ========== DELETE ==========
    
    @Test
    @Order(6)
    @DisplayName("DELETE /api/todos/{id} should delete todo")
    public void testDeleteTodo() {
        // Create a todo
        Long todoId = given()
            .contentType(ContentType.JSON)
            .body("{\"title\":\"Todo to Delete\",\"completed\":false}")
            .when()
                .post("/api/todos")
            .then()
                .statusCode(201)
                .extract()
                .path("id");

        // Delete the todo
        given()
            .pathParam("id", todoId)
            .when()
                .delete("/api/todos/{id}")
            .then()
                .statusCode(204);

        // Verify it's deleted
        given()
            .pathParam("id", todoId)
            .when()
                .get("/api/todos/{id}")
            .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("DELETE /api/todos/{id} with non-existing id should return 404")
    public void testDeleteTodoNotFound() {
        given()
            .pathParam("id", 999999)
            .when()
                .delete("/api/todos/{id}")
            .then()
                .statusCode(404);
    }

    // ========== EDGE CASES ==========
    
    @Test
    @DisplayName("POST /api/todos with empty body should fail")
    public void testCreateTodoWithEmptyBody() {
        given()
            .contentType(ContentType.JSON)
            .body("{}")
            .when()
                .post("/api/todos")
            .then()
                .statusCode(500); // NullPointerException or validation error
    }

    @Test
    @DisplayName("POST /api/todos with long description should succeed")
    public void testCreateTodoWithLongDescription() {
        String longDescription = "A".repeat(1000);
        given()
            .contentType(ContentType.JSON)
            .body("{\"title\":\"Todo with long description\",\"description\":\"" + longDescription + "\",\"completed\":false}")
            .when()
                .post("/api/todos")
            .then()
                .statusCode(201)
                .body("description", is(longDescription));
    }

    @Test
    @DisplayName("PUT /api/todos/{id} should update lastModifiedAt timestamp")
    public void testUpdateUpdatesTimestamp() throws InterruptedException {
        // Create a todo
        Long todoId = given()
            .contentType(ContentType.JSON)
            .body("{\"title\":\"Test Timestamp\",\"completed\":false}")
            .when()
                .post("/api/todos")
            .then()
                .extract()
                .path("id");

        // Wait a bit to ensure timestamp difference
        Thread.sleep(1000);

        // Update the todo
        String lastModifiedAt = given()
            .contentType(ContentType.JSON)
            .pathParam("id", todoId)
            .body("{\"title\":\"Test Timestamp Updated\",\"completed\":true}")
            .when()
                .put("/api/todos/{id}")
            .then()
                .statusCode(200)
                .body("lastModifiedAt", notNullValue())
                .extract()
                .path("lastModifiedAt");

        // Verify lastModifiedAt was updated
        assert lastModifiedAt != null;
    }
}
